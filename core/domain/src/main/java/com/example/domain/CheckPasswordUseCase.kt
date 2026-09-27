package com.example.domain

import com.example.dataApi.repository.SystemRepository
import javax.inject.Inject

class CheckPasswordUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(password: String) = systemRepository.checkPassword(password = password)
}
