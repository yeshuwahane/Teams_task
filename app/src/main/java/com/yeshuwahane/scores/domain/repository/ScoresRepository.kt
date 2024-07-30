package com.yeshuwahane.scores.domain.repository

import com.yeshuwahane.scores.data.model.gamecarddata.GameCardData
import com.yeshuwahane.scores.data.model.schedule.SchedulesDto
import com.yeshuwahane.scores.data.model.teams.TeamsData

interface ScoresRepository {

    suspend fun getSchedule():SchedulesDto

    suspend fun getTeams(): TeamsData

    suspend fun getGameCards():GameCardData
}