package com.yeshuwahane.scores.presentation

import kotlinx.serialization.Serializable


@Serializable
data class TeamsResponse(
    val data: TeamData
)

@Serializable
data class TeamData(
    val teams: List<Team>
)

@Serializable
data class Team(
    val tid: String? ="",
    val logo: String? = ""
)
