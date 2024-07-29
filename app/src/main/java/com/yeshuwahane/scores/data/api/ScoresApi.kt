package com.yeshuwahane.scores.data.api

import com.yeshuwahane.scores.data.Constant
import com.yeshuwahane.scores.data.model.gamecarddata.GameCardData
import com.yeshuwahane.scores.data.model.schedule.SchedulesData
import com.yeshuwahane.scores.data.model.teams.TeamsData
import retrofit2.http.GET

interface ScoresApi {


    @GET(Constant.gameScheduleUrl)
    suspend fun getSchedule(): SchedulesData

    @GET(Constant.teamsUrl)
    suspend fun getTeams(): TeamsData

    @GET(Constant.gameCardUrl)
    suspend fun getGameCards(): GameCardData
}