package com.example.domain

import com.example.data_api.repository.GithubRepository
import com.example.model.github.GithubDeviceCode
import javax.inject.Inject

class RequestGithubDeviceCodeUseCase
@Inject
constructor(
    private val githubRepository: GithubRepository,
) {
    suspend operator fun invoke(): GithubDeviceCode = githubRepository.requestDeviceCode()
}
