package com.example.model.github

import java.time.LocalDate

data class GithubContributionDay(
    val date: LocalDate,
    val contributionCount: Int,
)