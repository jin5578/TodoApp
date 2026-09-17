package com.example.github_auth

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DisconnectGithubUseCase
import com.example.domain.GetGithubUsernameUseCase
import com.example.domain.PollGithubAccessTokenUseCase
import com.example.domain.RequestGithubDeviceCodeUseCase
import com.example.github_auth.model.GithubAuthErrorReason
import com.example.github_auth.model.GithubAuthUiState
import com.example.model.github.GithubPollResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.io.IOException
import javax.inject.Inject

private const val MAX_CONSECUTIVE_POLL_NETWORK_FAILURES = 3
private const val MIN_POLL_INTERVAL_MILLIS = 2_000L

@HiltViewModel
class GithubAuthViewModel @Inject constructor(
    private val getGithubUsernameUseCase: GetGithubUsernameUseCase,
    private val requestGithubDeviceCodeUseCase: RequestGithubDeviceCodeUseCase,
    private val pollGithubAccessTokenUseCase: PollGithubAccessTokenUseCase,
    private val disconnectGithubUseCase: DisconnectGithubUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<GithubAuthUiState> =
        MutableStateFlow(value = GithubAuthUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var pollingJob: Job? = null
    private val pollTrigger = Channel<Unit>(capacity = Channel.CONFLATED)
    private var lastPolledAtMillis = 0L

    @Volatile
    private var isAppInForeground = true

    private val processLifecycleObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                isAppInForeground = true
                pollTrigger.trySend(Unit)
            }
            Lifecycle.Event.ON_STOP -> isAppInForeground = false
            else -> Unit
        }
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            observer = processLifecycleObserver
        )
        checkConnectionState()
    }

    override fun onCleared() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(observer = processLifecycleObserver)
    }

    fun disconnect() = viewModelScope.launch {
        pollingJob?.cancel()
        disconnectGithubUseCase()
        _uiState.value = GithubAuthUiState.Loading
        checkConnectionState()
    }

    fun retry() {
        pollingJob?.cancel()
        _uiState.value = GithubAuthUiState.Loading
        checkConnectionState()
    }

    private fun checkConnectionState() =
        viewModelScope.launch {
            val username = getGithubUsernameUseCase().first()
            if (username != null) {
                _uiState.value =
                    GithubAuthUiState.Connected(username = username)
            } else {
                startDeviceFlow()
            }
        }

    private fun startDeviceFlow() =
        viewModelScope.launch {
            runCatching { requestGithubDeviceCodeUseCase() }
                .onSuccess { deviceCode ->
                    _uiState.value = GithubAuthUiState.AwaitingUser(
                        userCode = deviceCode.userCode,
                        verificationUri = deviceCode.verificationUri,
                        isPolling = false,
                    )
                    startPolling(
                        deviceCode = deviceCode.deviceCode,
                        intervalSeconds = deviceCode.intervalSeconds,
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = GithubAuthUiState.Error(
                        reason = throwable.toGithubAuthErrorReason()
                    )
                }
        }

    private fun startPolling(deviceCode: String, intervalSeconds: Int) {
        lastPolledAtMillis = 0L

        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            var currentIntervalSeconds = intervalSeconds
            var consecutiveNetworkFailures = 0
            while (true) {
                withTimeoutOrNull(timeMillis = currentIntervalSeconds * 1000L) {
                    pollTrigger.receive()
                }

                if (!isAppInForeground) continue

                val sinceLastPollMillis = System.currentTimeMillis() - lastPolledAtMillis
                if (sinceLastPollMillis < MIN_POLL_INTERVAL_MILLIS) continue
                lastPolledAtMillis = System.currentTimeMillis()

                val result = runCatching {
                    pollGithubAccessTokenUseCase(deviceCode = deviceCode)
                }.getOrElse { throwable ->
                    if (throwable is IOException &&
                        ++consecutiveNetworkFailures < MAX_CONSECUTIVE_POLL_NETWORK_FAILURES
                    ) {
                        return@getOrElse null
                    }
                    _uiState.value =
                        GithubAuthUiState.Error(reason = throwable.toGithubAuthErrorReason())
                    return@launch
                } ?: continue
                consecutiveNetworkFailures = 0

                when (result) {
                    is GithubPollResult.Pending -> {
                        val awaitingState = _uiState.value
                        if (awaitingState is GithubAuthUiState.AwaitingUser && !awaitingState.isPolling) {
                            _uiState.value =
                                awaitingState.copy(isPolling = true)
                        }
                    }

                    is GithubPollResult.SlowDown -> {
                        currentIntervalSeconds += 5
                    }

                    is GithubPollResult.Success -> {
                        _uiState.value = GithubAuthUiState.Success
                        return@launch
                    }

                    is GithubPollResult.Expired -> {
                        _uiState.value =
                            GithubAuthUiState.Error(reason = GithubAuthErrorReason.EXPIRED)
                        return@launch
                    }

                    is GithubPollResult.Denied -> {
                        _uiState.value =
                            GithubAuthUiState.Error(reason = GithubAuthErrorReason.DENIED)
                        return@launch
                    }
                }
            }
        }
    }


    private fun Throwable.toGithubAuthErrorReason(): GithubAuthErrorReason =
        if (this is IOException) GithubAuthErrorReason.NETWORK
        else GithubAuthErrorReason.UNKNOWN
}