package com.yeshuwahane.scores.presentation.schedule

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ScheduleScreen() {
    Column(modifier = Modifier.fillMaxSize()){
        DateComponent()

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GameCard("AWAY | SAT JUL 01 | FINAL", "MIA", 120, "WAS", 102, true)
            GameCard("3RD QTR | 00:16.3", "PHX", 100, "MIA", 134, false)
            GameCard("HOME | MON JUL 13 | 7:30 PM", "LAL", -1, "MIA", -1, false, true)
            GameCard("HOME | THU JUL 6 | 8:30 PM", "MIN", -1, "MIA", -1, false, true)
            GameCard("AWAY | TUE JUL 18 | 8:30 PM", "MIA", -1, "CHI", -1, true)
            GameCard("AWAY | TUE JUL 18 | 8:30 PM", "MIA", -1, "CHI", -1, true)
            GameCard("AWAY | TUE JUL 18 | 8:30 PM", "MIA", -1, "CHI", -1, true)
            GameCard("AWAY | TUE JUL 18 | 8:30 PM", "MIA", -1, "CHI", -1, true)
        }
    }



}


@Composable
fun DateComponent() {
    var selectedMonthIndex by remember { mutableStateOf(6) }  // July 2023 is the 7th month (index 6)
    var expanded by remember { mutableStateOf(false) }

    val months = listOf(
        "January 2023",
        "February 2023",
        "March 2023",
        "April 2023",
        "May 2023",
        "June 2023",
        "July 2023",
        "August 2023",
        "September 2023",
        "October 2023",
        "November 2023",
        "December 2023"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
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
                    selectedMonthIndex -= 1
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
                    selectedMonthIndex += 1
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
                modifier = Modifier
                    .background(Color.DarkGray)
//                    .align(Alignment.Center)
            ) {
                months.forEachIndexed { index, month ->
                    DropdownMenuItem(
                        onClick = {
                            selectedMonthIndex = index
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
    gameInfo: String,
    team1: String,
    score1: Int,
    team2: String,
    score2: Int,
    isAway: Boolean,
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = team1, color = Color.White, fontWeight = FontWeight.Bold)
                    if (score1 != -1) Text(
                        text = score1.toString(), color = Color.White, fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "@", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = team2, color = Color.White, fontWeight = FontWeight.Bold)
                    if (score2 != -1) Text(
                        text = score2.toString(), color = Color.White, fontWeight = FontWeight.Bold
                    )
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


