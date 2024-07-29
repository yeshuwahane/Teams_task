package com.yeshuwahane.scores.presentation.games

import kotlinx.serialization.Serializable

data class GameState(
    val s: String
)



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
    val uid: String,
    val year: Int,
    val league_id: String,
    val season_id: String,
    val ist_group: String,
    val tid: String,
    val tn: String,
    val ta: String,
    val tc: String,
    val di: String,
    val co: String,
    val sta: String,
    val logo: String,
    val color: String
)
