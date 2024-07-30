package com.yeshuwahane.scores.presentation.schedule

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.yeshuwahane.scores.domain.loadJSONFromAsset
import com.yeshuwahane.scores.presentation.games.TeamsResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.Month
import java.time.OffsetDateTime


@Composable
fun ScheduleScreen(context: Context) {
    val viewModel: ScheduleViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getSchedule(context)
    }

    val uiState by viewModel.uiState.collectAsState()
    var selectedMonthIndex by remember { mutableStateOf(6) } // Default to July
    val scrollState = rememberLazyListState()

    // Update selectedMonthIndex based on scroll state
    LaunchedEffect(remember { derivedStateOf { scrollState.firstVisibleItemIndex } }) {
        // Assume each item is of fixed height and determine the month based on the scroll position
        val visibleItemIndex = scrollState.firstVisibleItemIndex
        val itemHeight = 100 // Replace with the actual height of your game card
        val monthsPerPage = 5 // Number of items that can fit in a month

        // Update month index based on visible item
        selectedMonthIndex = (visibleItemIndex / monthsPerPage).coerceIn(0, 11)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DateComponent(selectedMonthIndex) { newMonthIndex ->
            selectedMonthIndex = newMonthIndex
        }

        uiState.schedules.let { scheduleData ->
            ScheduleStatic(scheduleData, selectedMonthIndex, scrollState)
        }
    }
}

@Composable
fun ScheduleStatic(schedules: List<Schedule>, selectedMonthIndex: Int, scrollState: LazyListState) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    // Debugging log for schedule count
    Log.d("ScheduleStatic", "Total schedules: ${schedules.size}")

    // Convert selectedMonthIndex to Month enum
    val selectedMonth = Month.of(selectedMonthIndex + 1) // Months are 1-based (January is 1)

    val filteredSchedules = schedules.filter { schedule ->
        try {
            val dateTime = OffsetDateTime.parse(schedule.gametime)
            val scheduleMonth = dateTime.month
            // Log the parsed month and the index
            Log.d("ScheduleStatic", "Gametime: ${schedule.gametime}, Month: ${scheduleMonth}, Index: $selectedMonthIndex")

            scheduleMonth == selectedMonth
        } catch (e: Exception) {
            // Log parsing errors if any
            Log.e("ScheduleStatic", "Date parsing error for ${schedule.gametime}", e)
            false
        }
    }

    // Debugging log for filtered schedule count
    Log.d("ScheduleStatic", "Filtered schedules for month index $selectedMonthIndex: ${filteredSchedules.size}")

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filteredSchedules) { schedule ->
            GameCard(
                gameInfo = schedule.gametime,
                team1 = schedule.h.tc.orEmpty(),
                score1 = schedule.h.s?.toIntOrNull() ?: 0, // Safe conversion
                team2 = schedule.v.tc.orEmpty(),
                score2 = schedule.v.s?.toIntOrNull() ?: 0, // Safe conversion
                showTicket = !schedule.buy_ticket.isNullOrBlank(),
                teamLogo1 = "homeLogo",
                teamLogo2 = "awayLogo"
            )
            // Debugging log for each game card
            Log.d("ScheduleStatic", "Displaying game: ${schedule.gametime}, Home: ${schedule.h.tc}, Away: ${schedule.v.tc}")
        }
    }
}







@Composable
fun DateComponent(selectedMonthIndex: Int, onMonthChange: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            IconButton(onClick = {
                if (selectedMonthIndex > 0) {
                    onMonthChange(selectedMonthIndex - 1)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Arrow Up",
                    tint = Color.White
                )
            }
            Text(
                text = months[selectedMonthIndex],
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                if (selectedMonthIndex < months.size - 1) {
                    onMonthChange(selectedMonthIndex + 1)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color.White
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.DarkGray)
        ) {
            months.forEachIndexed { index, month ->
                DropdownMenuItem(
                    onClick = {
                        onMonthChange(index)
                        expanded = false
                    },
                    text = {
                        Text(text = month, color = Color.White, textAlign = TextAlign.Center)
                    }
                )
            }
        }
    }
}





@Composable
fun GameCard(
    gameInfo: String,
    team1: String,
    score1: Int,
    team2: String,
    score2: Int,
    teamLogo1: String,
    teamLogo2: String,
    showTicket: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = gameInfo, color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (teamLogo1.isNotEmpty()) {
                            AsyncImage(
                                model = teamLogo1, contentDescription = team1,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(text = team1, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    if (score1 != -1) {
                        Text(text = score1.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                Text(
                    text = "vs", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Light
                )
                Row {
                    if (score2 != -1) {
                        Text(text = score2.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (teamLogo2.isNotEmpty()) {
                            AsyncImage(
                                model = teamLogo2, contentDescription = team2,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(text = team2, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (showTicket) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO: Add Ticket Purchase Logic */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "BUY TICKETS ON ticketmaster", color = Color.Black)
                }
            }
        }
    }
}


