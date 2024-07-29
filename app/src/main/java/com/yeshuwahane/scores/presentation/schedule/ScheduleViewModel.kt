package com.yeshuwahane.scores.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.scores.domain.repository.ScoresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val repository: ScoresRepository) : ViewModel() {


    fun getSchedule() {
        viewModelScope.launch {

            val response = repository.getSchedule()

        }
    }


}