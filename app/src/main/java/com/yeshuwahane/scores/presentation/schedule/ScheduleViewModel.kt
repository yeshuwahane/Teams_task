package com.yeshuwahane.scores.presentation.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.scores.domain.repository.ScoresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val repository: ScoresRepository) : ViewModel() {

    private var _uiState = MutableStateFlow(ScheduleState(data = emptyList()))
    val uiState = _uiState.asStateFlow()

    fun getSchedule() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSchedule()
            _uiState.update {
                it.copy(
                    data = response.data.scheduleItems
                )
            }

            Log.d("alien","scores: ${uiState.value.data}")

        }
    }


}