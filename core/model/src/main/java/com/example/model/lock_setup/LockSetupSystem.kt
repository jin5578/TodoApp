package com.example.model.lock_setup

import java.util.Locale

data class LockSetupSystem(
    val hasExistingPassword: Boolean,
    val locale: Locale
)