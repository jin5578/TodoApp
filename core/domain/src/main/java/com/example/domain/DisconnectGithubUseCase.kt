package com.example.domain

import com.example.data_api.repository.GithubRepository
import javax.inject.Inject

class DisconnectGithubUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) {
    suspend operator fun invoke() =
        githubRepository.disconnect()
}