package com.example.domain

import com.example.dataApi.repository.SystemRepository
import java.util.Locale
import javax.inject.Inject

class UpdateLocaleUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    suspend operator fun invoke(locale: Locale) = systemRepository.updateLocale(locale = locale)
}
