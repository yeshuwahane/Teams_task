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
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import java.time.Month
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun ScheduleScreen(context: Context) {
    val viewModel: ScheduleViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.getSchedule(context)
    }

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    var itemHeight by remember { mutableStateOf(0) }
    var initialScrollIndex by remember { mutableStateOf(-1) } // To store the index for initial scroll
    var selectedMonthIndex by remember { mutableStateOf(0) } // Manage the selected month index

    // Define the app team ID here
    val appTeamId = "1610612748" // Replace with your actual app team ID

    // Group schedules by month
    val schedulesByMonth = uiState.schedules.groupBy {
        try {
            OffsetDateTime.parse(it.gametime).monthValue - 1
        } catch (e: Exception) {
            Log.e("ScheduleScreen", "Date parsing error for ${it.gametime}", e)
            -1
        }
    }.filterKeys { it in 0..11 }.toSortedMap()

    // List of available months
    val availableMonths = schedulesByMonth.keys.toList()

    // Flatten schedules into a single list for LazyColumn
    val flatSchedules = schedulesByMonth.flatMap { it.value }

    // Calculate the index for the current date
    val currentDate = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC)
    val currentIndex = flatSchedules.indexOfFirst { schedule ->
        try {
            val gameDate = OffsetDateTime.parse(schedule.gametime).withOffsetSameInstant(ZoneOffset.UTC)
            gameDate.isAfter(currentDate) || gameDate.isEqual(currentDate)
        } catch (e: Exception) {
            Log.e("ScheduleScreen", "Date parsing error for ${schedule.gametime}", e)
            false
        }
    }

    // Set the initial scroll index on first composition
    LaunchedEffect(flatSchedules) {
        if (currentIndex != -1) {
            initialScrollIndex = currentIndex
            scrollState.scrollToItem(initialScrollIndex)
        }
    }

    // Update scroll state to position based on current date
    LaunchedEffect(currentIndex) {
        if (currentIndex != -1 && itemHeight > 0 && initialScrollIndex == -1) {
            // Scroll to the position of the current date if not yet scrolled
            scrollState.animateScrollToItem(currentIndex)
        }
    }

    // Update selectedMonthIndex based on scroll state
    LaunchedEffect(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset, itemHeight) {
        if (itemHeight > 0 && schedulesByMonth.isNotEmpty()) {
            val visibleItemIndex = scrollState.firstVisibleItemIndex
            val positionOffset = scrollState.firstVisibleItemScrollOffset.toFloat()
            val itemIndex = (visibleItemIndex + positionOffset / itemHeight).toInt()

            // Find the corresponding month for the visible item
            val monthCounts = schedulesByMonth.map { it.value.size }
            var cumulativeItems = 0
            selectedMonthIndex = monthCounts.indexOfFirst {
                cumulativeItems += it
                itemIndex < cumulativeItems
            }.coerceIn(0, monthCounts.size - 1)
        }
    }

    // Scroll to the position if the month changes
    LaunchedEffect(selectedMonthIndex) {
        if (schedulesByMonth.isNotEmpty() && itemHeight > 0) {
            val monthCounts = schedulesByMonth.map { it.value.size }
            val scrollToIndex = monthCounts.take(selectedMonthIndex).sum()
            scrollState.animateScrollToItem(scrollToIndex)
        }
    }

    if (availableMonths.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            DateComponent(selectedMonthIndex, availableMonths) { newMonthIndex ->
                selectedMonthIndex = newMonthIndex
            }

            ScheduleStatic(flatSchedules, scrollState, appTeamId) { height ->
                itemHeight = height
            }
        }
    } else {
        // Handle case where there are no schedules
        Text(
            text = "No schedules available",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}






@Composable
fun ScheduleStatic(
    schedules: List<Schedule>,
    scrollState: LazyListState,
    appTeamId: String, // Add this parameter
    onItemHeightChange: (Int) -> Unit
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(schedules) { schedule ->
            GameCard(
                gameInfo = schedule.gametime,
                team1 = schedule.h.tc.orEmpty(),
                score1 = schedule.h.s?.toIntOrNull() ?: 0,
                team2 = schedule.v.tc.orEmpty(),
                score2 = schedule.v.s?.toIntOrNull() ?: 0,
                showTicket = !schedule.buy_ticket.isNullOrBlank(),
                teamLogo1 = "homeLogo", // Replace with actual team logo URL
                teamLogo2 = "awayLogo", // Replace with actual team logo URL
                isHomeTeam = schedule.h.tid == appTeamId, // Determine if the app team is the home team
                onGloballyPositioned = { coordinates ->
                    // Measure item height when it is first positioned
                    val height = coordinates.size.height
                    if (height > 0) {
                        onItemHeightChange(height)
                    }
                }
            )
        }
    }
}






@Composable
fun DateComponent(selectedMonthIndex: Int, availableMonths: List<Int>, onMonthChange: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val allMonths = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    // Filter the list of months to only include those that are available
    val months = availableMonths.map { allMonths[it] }

    if (months.isNotEmpty()) {
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
                    text = months.getOrElse(selectedMonthIndex) { "" },
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
    showTicket: Boolean = false,
    isHomeTeam: Boolean, // Add this parameter
    onGloballyPositioned: (LayoutCoordinates) -> Unit
) {
    // Convert game info time from UTC to local time
    val localGameTime = convertUtcToLocal(gameInfo)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .onGloballyPositioned(onGloballyPositioned),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = localGameTime, color = Color.Gray, fontSize = 12.sp)
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
                if (isHomeTeam) {
                    Text(
                        text = "VS",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }else{
                    Text(
                        text = "@",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
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







fun convertUtcToLocal(utcDateTime: String): String {
    // Parse the UTC date-time string
    val utcDateTimeObj = OffsetDateTime.parse(utcDateTime)

    // Get the system's default time zone
    val localZoneId = ZoneId.systemDefault()

    // Convert UTC to local time
    val localDateTime = utcDateTimeObj.atZoneSameInstant(localZoneId)

    // Define your desired format
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    // Format the local date-time to a string
    return localDateTime.format(formatter)
}


