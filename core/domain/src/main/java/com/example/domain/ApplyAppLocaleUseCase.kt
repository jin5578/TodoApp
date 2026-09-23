package com.example.domain

import android.content.Context
import com.example.model.LanguageType
import com.example.utils.AppLocaleManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplyAppLocaleUseCase
@Inject
constructor(
    @param:ApplicationContext private val context: Context,
) {
    operator fun invoke(languageType: LanguageType) {
        AppLocaleManager.applyLocale(
            context = context,
            languageType = languageType,
        )
    }
}
