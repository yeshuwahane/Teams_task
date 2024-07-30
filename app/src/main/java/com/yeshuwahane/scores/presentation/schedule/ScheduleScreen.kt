package com.yeshuwahane.scores.presentation.schedule

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
import androidx.compose.foundation.lazy.items
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
import coil.compose.AsyncImage


@Composable
fun ScheduleScreen() {
    var selectedMonthIndex by remember { mutableStateOf(6) } // Default to July

    val schedules = listOf(
        Schedule("SAT JUL 01 | 7:00 PM", "FINAL", "MIA", 120, "WAS", 102, "", ""),
        Schedule("3RD QTR | 00:16.3", "IN PROGRESS", "PHX", 100, "MIA", 134, "", ""),
        Schedule("MON JUL 13 | 7:30 PM", "SCHEDULED", "LAL", -1, "MIA", -1, "", ""),
        Schedule("THU JUL 6 | 8:30 PM", "SCHEDULED", "MIN", -1, "MIA", -1, "", ""),
        Schedule("TUE JUL 18 | 8:30 PM", "SCHEDULED", "MIA", -1, "CHI", -1, "", ""),
        Schedule("TUE JUN 01 | 8:30 PM", "SCHEDULED", "MIA", -1, "CHI", -1, "", ""),
        Schedule("TUE MAR 08 | 8:30 PM", "SCHEDULED", "MIA", -1, "CHI", -1, "", ""),
        Schedule("TUE FEB 11 | 8:30 PM", "SCHEDULED", "MIA", -1, "CHI", -1, "", ""),
        Schedule("WED AUG 02 | 7:00 PM", "FINAL", "NYK", 110, "BOS", 105, "", ""),
        Schedule("THU SEP 05 | 7:00 PM", "FINAL", "GSW", 120, "LAC", 115, "", ""),
        Schedule("FRI OCT 10 | 7:00 PM", "FINAL", "HOU", 100, "DAL", 95, "", ""),
        Schedule("SAT NOV 15 | 7:00 PM", "FINAL", "SAS", 130, "DEN", 125, "", ""),
        Schedule("SUN DEC 20 | 7:00 PM", "FINAL", "MIL", 140, "TOR", 135, "", "")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        DateComponent(selectedMonthIndex) { newMonthIndex ->
            selectedMonthIndex = newMonthIndex
        }
        ScheduleStatic(schedules = schedules, selectedMonthIndex)
    }
}

@Composable
fun ScheduleStatic(schedules: List<Schedule>, selectedMonthIndex: Int) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val filteredSchedules = schedules.filter { schedule ->
        val scheduleMonth = schedule.gameTime.split(" ")[1].uppercase()
        val monthIndex = months.indexOf(scheduleMonth.capitalize())
        Log.d("ScheduleStatic", "Filtering schedule: $scheduleMonth -> monthIndex: $monthIndex == selectedMonthIndex: $selectedMonthIndex")
        monthIndex == selectedMonthIndex
    }

    Log.d("ScheduleStatic", "Filtered schedules count: ${filteredSchedules.size}")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filteredSchedules) { schedule ->
            GameCard(
                gameTime = schedule.gameTime,
                gameInfo = schedule.status,
                team1 = schedule.team1,
                score1 = schedule.score1,
                team2 = schedule.team2,
                score2 = schedule.score2,
                teamLogo1 = schedule.teamLogo1,
                teamLogo2 = schedule.teamLogo2
            )

            Log.d("alien","game card: ${schedule.status}")
        }
    }
}

@Composable
fun DateComponent(selectedMonthIndex: Int, onMonthChange: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val months = listOf(
        "January", "February", "March", "April",
        "May", "June", "July", "August",
        "September", "October", "November", "December"
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
                if (selectedMonthIndex > 0) onMonthChange(selectedMonthIndex - 1)
            }) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Arrow Up", tint = Color.White)
            }
            Text(
                text = months[selectedMonthIndex],
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                if (selectedMonthIndex < months.size - 1) onMonthChange(selectedMonthIndex + 1)
            }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Arrow Down", tint = Color.White)
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
                    },
                )
            }
        }
    }
}

@Composable
fun GameCard(
    gameTime: String,
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
            Text(text = gameTime, color = Color.Gray, fontSize = 12.sp)
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







