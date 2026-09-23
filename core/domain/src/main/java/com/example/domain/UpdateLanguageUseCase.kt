package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.LanguageType
import java.util.Locale
import javax.inject.Inject

class UpdateLanguageUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
    private val updateLocaleUseCase: UpdateLocaleUseCase,
    private val applyAppLocaleUseCase: ApplyAppLocaleUseCase,
) {
    suspend operator fun invoke(languageType: LanguageType) {
        val locale =
            if (languageType == LanguageType.KOREAN) {
                Locale.KOREA
            } else {
                Locale.US
            }

        systemRepository.updateLanguage(languageType = languageType)

        updateLocaleUseCase(locale = locale)
        applyAppLocaleUseCase(languageType = languageType)
    }
}
