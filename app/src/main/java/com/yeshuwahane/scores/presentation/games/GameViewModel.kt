package com.yeshuwahane.scores.presentation.games

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.scores.data.repository.ScoresRepositoryImpl
import com.yeshuwahane.scores.domain.repository.ScoresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(val repositoryImpl: ScoresRepository): ViewModel() {

    fun getSchedules(){


    }

}