package com.yeshuwahane.scores.presentation.schedule

import kotlinx.serialization.Serializable



@Serializable
data class ScheduleState(
    val data: ScheduleData
)

@Serializable
data class ScheduleData(
    val schedules: List<Schedule>
)

@Serializable
data class Schedule(
    val uid: String,
    val year: Int,
    val league_id: String,
    val season_id: String,
    val gid: String,
    val gcode: String,
    val seri: String,
    val is_game_necessary: String,
    val gametime: String,
    val cl: String,
    val arena_name: String,
    val arena_city: String,
    val arena_state: String,
    val st: Int,
    val stt: String,
    val ppdst: String,
    val buy_ticket: String? ="",
    val buy_ticket_url: String? ="",
    val logo_url: String?,
    val hide: Boolean,
    val game_state: String?,
    val game_subtype: String?,
    val h: Team,
    val v: Team
)

@Serializable
data class Team(
    val tid: String? ="",
    val re: String? ="",
    val ta: String? ="",
    val tn: String? ="",
    val tc: String? ="",
    val s: String? ="",
    val ist_group: String?
)