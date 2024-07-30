package com.yeshuwahane.scores.data.model.schedule

data class ScheduleItem(
    val arena_city: String,
    val arena_name: String,
    val arena_state: String,
    val buy_ticket: String,
    val buy_ticket_url: String,
    val cl: String,
    val game_state: String,
    val game_subtype: String,
    val gametime: String,
    val gcode: String,
    val gid: String,
    val h: H,
    val hide: Boolean,
    val is_game_necessary: String,
    val league_id: String,
    val logo_url: Any,
    val ppdst: String,
    val season_id: String,
    val seri: String,
    val st: Int,
    val stt: String,
    val uid: String,
    val v: V,
    val year: Int
)