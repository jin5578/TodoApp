package com.example.domain

import com.example.data_api.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGithubUsernameUseCase
@Inject
constructor(
    private val githubRepository: GithubRepository,
) {
    operator fun invoke(): Flow<String?> = githubRepository.getGithubUsername()
}
