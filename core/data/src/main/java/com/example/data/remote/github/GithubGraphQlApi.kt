package com.example.data.remote.github

import com.example.model.github.GithubContributionDay
import java.time.LocalDate


private const val CONTRIBUTION_CALENDAR_QUERY = """
    query {
      viewer {
        login
        contributionsCollection {
          contributionCalendar {
            weeks {
              contributionDays {
                date
                contributionCount
              }
            }
          }
        }
      }
    }
"""

internal class GithubGraphQlApi(
    private val apiService: GithubApiService,
) {
    suspend fun fetchContributionCalendar(
        accessToken: String,
    ): Pair<String, List<GithubContributionDay>> {
        val response = apiService.postGraphQl(
            authorization = "Bearer $accessToken",
            body = GraphQlRequestBody(query = CONTRIBUTION_CALENDAR_QUERY),
        )
        val viewer =
            requireNotNull(value = response.data) { "GraphQL response had no data" }.viewer

        val days = viewer.contributionsCollection.contributionCalendar.weeks
            .flatMap { week -> week.contributionDays }
            .map { day ->
                GithubContributionDay(
                    date = LocalDate.parse(day.date),
                    contributionCount = day.contributionCount,
                )
            }

        return viewer.login to days
    }
}