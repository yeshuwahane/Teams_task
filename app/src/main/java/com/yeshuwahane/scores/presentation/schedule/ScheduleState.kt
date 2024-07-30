package com.yeshuwahane.scores.presentation.schedule

import com.yeshuwahane.scores.data.model.schedule.ScheduleItem

data class ScheduleState(
    val data: List<ScheduleItem>
)

data class Schedule(
    val gameTime: String,
    val status: String,
    val team1: String,
    val score1: Int,
    val team2: String,
    val score2: Int,
    val teamLogo1: String,
    val teamLogo2: String
)




