package com.example.domain

import com.example.data_api.repository.GithubRepository
import com.example.model.github.GithubContributionDay
import javax.inject.Inject

class GetGithubContributionsUseCase
@Inject
constructor(
    private val githubRepository: GithubRepository,
) {
    suspend operator fun invoke(): List<GithubContributionDay> = githubRepository.getContributionDays()
}
