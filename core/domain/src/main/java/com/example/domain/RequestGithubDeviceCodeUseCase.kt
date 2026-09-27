package com.example.domain

import com.example.dataApi.repository.GithubRepository
import com.example.model.github.GithubDeviceCode
import javax.inject.Inject

class RequestGithubDeviceCodeUseCase
@Inject
constructor(
    private val githubRepository: GithubRepository,
) {
    suspend operator fun invoke(): GithubDeviceCode = githubRepository.requestDeviceCode()
}
