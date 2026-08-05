package com.example.model.setting

import com.example.model.LanguageType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import java.time.LocalTime

data class SettingSystem(
    val languageType: LanguageType,
    val themeType: ThemeType,
    val sleepTime: LocalTime,
    val timePickerType: TimePickerType,
    val buildVersion: String,
)