package com.yeshuwahane.scores.di

import com.yeshuwahane.scores.data.api.RetrofitInstance
import com.yeshuwahane.scores.data.api.ScoresApi
import com.yeshuwahane.scores.data.repository.ScoresRepositoryImpl
import com.yeshuwahane.scores.domain.repository.ScoresRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ScoresModules {



    @Provides
    @Singleton
    fun provideScoresApi():ScoresApi{
        return RetrofitInstance.getRetrofitInstance().create(ScoresApi::class.java)
    }


    @Provides
    @Singleton
    fun provideScoreRepository(repositoryImpl: ScoresRepositoryImpl):ScoresRepository{
        return repositoryImpl
    }

}