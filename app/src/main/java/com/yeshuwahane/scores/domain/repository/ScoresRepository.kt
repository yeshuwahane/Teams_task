package com.yeshuwahane.scores.domain.repository

import com.yeshuwahane.scores.data.model.gamecarddata.GameCardData
import com.yeshuwahane.scores.data.model.schedule.SchedulesData
import com.yeshuwahane.scores.data.model.teams.TeamsData

interface ScoresRepository {

    suspend fun getSchedule():SchedulesData

    suspend fun getTeams(): TeamsData

    suspend fun getGameCards():GameCardData
}