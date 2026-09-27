package com.example.tasks

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.designSystem.theme.TodoTheme
import com.example.designSystem.R as DesignSystemR

@Composable
internal fun TasksBiometricScreen(
    onBiometricAuthSucceeded: () -> Unit,
    onBiometricAuthError: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val isBiometricAvailable = remember(key1 = activity) {
        activity != null &&
            BiometricManager.from(activity).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG,
            ) == BiometricManager.BIOMETRIC_SUCCESS
    }

    if (activity != null && isBiometricAvailable) {
        val biometricPrompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    onBiometricAuthSucceeded()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == 13) onBiometricAuthError()
                }
            },
        )
        val title =
            stringResource(id = DesignSystemR.string.please_authenticate_your_biometric_info)
        val negativeButtonText =
            stringResource(id = DesignSystemR.string.authenticate_with_password)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setNegativeButtonText(negativeButtonText)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksBiometricScreenPreview() {
    TodoTheme {
        TasksBiometricScreen(
            onBiometricAuthSucceeded = {},
            onBiometricAuthError = {},
        )
    }
}
