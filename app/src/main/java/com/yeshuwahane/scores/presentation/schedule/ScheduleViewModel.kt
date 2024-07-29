package com.yeshuwahane.scores.presentation.schedule

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.scores.domain.loadJSONFromAsset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor() : ViewModel() {

    private var _uiState = MutableStateFlow(ScheduleData(schedules = emptyList()))
    val uiState = _uiState.asStateFlow()

    object JsonUtil {
        val jsonInstance: Json = Json { ignoreUnknownKeys = true }
    }

    fun getSchedule(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonString = loadJSONFromAsset(context, "Schedule.json")
            Log.d("MainActivity", "JSON String: $jsonString")

            val scheduleResponse = jsonString?.let {
                try {
                    JsonUtil.jsonInstance.decodeFromString<ScheduleState>(it)
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error parsing JSON", e)
                    null
                }
            }

            scheduleResponse?.let {
                _uiState.update { currentState ->
                    currentState.copy(schedules = it.data.schedules)
                }
            }
        }
    }
}