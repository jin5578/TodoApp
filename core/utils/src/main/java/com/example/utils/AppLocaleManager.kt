package com.example.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.model.LanguageType

object AppLocaleManager {
    fun applyLocale(
        context: Context,
        languageType: LanguageType,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val platformLocaleManager =
                context.getSystemService(LocaleManager::class.java)
            val currentTag =
                platformLocaleManager
                    ?.applicationLocales
                    ?.toLanguageTags()
                    .orEmpty()
            if (currentTag != languageType.languageTag) {
                platformLocaleManager?.applicationLocales =
                    LocaleList.forLanguageTags(languageType.languageTag)
            }
        } else {
            val currentTag =
                AppCompatDelegate.getApplicationLocales().toLanguageTags()
            if (currentTag != languageType.languageTag) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(languageType.languageTag),
                )
            }
        }
    }
}
