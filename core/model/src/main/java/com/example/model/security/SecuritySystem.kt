package com.example.model.security

data class SecuritySystem(
    val hasExistingPassword: Boolean,
    val hasBiometricEnabled: Boolean,
)
