package com.example.domain

import com.example.dataApi.repository.GithubRepository
import com.example.model.github.GithubPollResult
import javax.inject.Inject

class PollGithubAccessTokenUseCase
@Inject
constructor(
    private val githubRepository: GithubRepository,
) {
    suspend operator fun invoke(deviceCode: String): GithubPollResult = githubRepository.pollAccessToken(deviceCode = deviceCode)
}
