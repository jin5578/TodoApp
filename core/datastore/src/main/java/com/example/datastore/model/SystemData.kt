package com.example.datastore.model

data class SystemData(
    val sleepTime: String,
    val sortByType: String,
    val languageType: String,
    val themeType: String,
    val timePickerType: String,
    val locale: String,
    val buildVersion: String,
    val password: String,
    val isBiometricEnabled: Boolean
)