package com.yeshuwahane.scores.data.repository

import com.yeshuwahane.scores.data.api.ScoresApi
import com.yeshuwahane.scores.data.model.gamecarddata.GameCardData
import com.yeshuwahane.scores.data.model.schedule.SchedulesDto
import com.yeshuwahane.scores.data.model.teams.TeamsData
import com.yeshuwahane.scores.domain.repository.ScoresRepository
import javax.inject.Inject

class ScoresRepositoryImpl @Inject constructor(val api: ScoresApi) : ScoresRepository {
    override suspend fun getSchedule(): SchedulesDto {
        return api.getSchedule()
    }

    override suspend fun getTeams(): TeamsData {
        return api.getTeams()
    }

    override suspend fun getGameCards(): GameCardData {
        return api.getGameCards()
    }
}