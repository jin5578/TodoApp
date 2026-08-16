package com.example.todoapp

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.domain.ApplyAppLocaleUseCase
import com.example.domain.GetSettingDataUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TodoApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var getSettingDataUseCase: GetSettingDataUseCase

    @Inject
    lateinit var applyAppLocaleUseCase: ApplyAppLocaleUseCase

    private val applicationScope =
        CoroutineScope(context = SupervisorJob() + Dispatchers.Main.immediate)

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory = workerFactory)
            .setMinimumLoggingLevel(loggingLevel = Log.INFO)
            .setExecutor(executor = Dispatchers.Default.asExecutor())
            .build()

    override fun onCreate() {
        super.onCreate()
        initTimber()
        applyPersistedLocale()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(tree = Timber.DebugTree())
    }

    private fun applyPersistedLocale() =
        applicationScope.launch {
            val languageType = getSettingDataUseCase().first().languageType
            applyAppLocaleUseCase(languageType = languageType)
        }
}