package com.example.model.lockSetup

import java.util.Locale

data class LockSetupSystem(
    val hasExistingPassword: Boolean,
    val locale: Locale,
)
