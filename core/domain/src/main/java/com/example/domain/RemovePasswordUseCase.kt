package com.example.domain

import com.example.data_api.repository.SystemRepository
import javax.inject.Inject

class RemovePasswordUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke() =
        systemRepository.removePassword()
}