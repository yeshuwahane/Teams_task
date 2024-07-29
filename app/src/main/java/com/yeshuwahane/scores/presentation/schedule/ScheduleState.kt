package com.yeshuwahane.scores.presentation.schedule

import com.yeshuwahane.scores.data.model.schedule.Schedule
import com.yeshuwahane.scores.data.model.schedule.SchedulesData

data class ScheduleState(
    val gameInfo: String,
    val team1: String,
    val score1: Int,
    val team2: String,
    val score2: Int,
    val isAway: Boolean,
    val showTicket: Boolean = false
)




