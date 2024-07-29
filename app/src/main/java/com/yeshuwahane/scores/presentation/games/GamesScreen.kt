package com.yeshuwahane.scores.presentation.games

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GamesScreen() {
    MyApp()

    val viewModel : GameViewModel = viewModel()

    viewModel.getSchedules()
}


@Composable
fun MyApp() {
    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        GameCardsRow()
    }
}

@Composable
fun GameCardsRow() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PastGameCard()
        UpcomingGameCardHome()
        UpcomingGameCardAway()
        FullScheduleCard()
    }
}

@Composable
fun PastGameCard() {
    val darkRed = Color(0xFF8B0000)
    Card(
        modifier = Modifier.size(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkRed
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "MIA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "126", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 36.sp)
            Text(text = "VS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(text = "TOR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "120", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 36.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)) {
                Text(text = "GAME RECAP", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun UpcomingGameCardHome() {
    Card(
        modifier = Modifier.size(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "LAL VS MIA", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "SAT OCT 05 | 7:00 PM", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CountdownTimer(days = 2, hours = 21, minutes = 57)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                Text(text = "BUY TICKETS ON ticketmaster", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun UpcomingGameCardAway() {
    Card(
        modifier = Modifier.size(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "MIA @ LAL", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "SAT OCT 05 | 7:00 PM", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CountdownTimer(days = 2, hours = 21, minutes = 57)
            }
        }
    }
}

@Composable
fun FullScheduleCard() {
    Card(
        modifier = Modifier.size(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "FULL HEAT SCHEDULE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "2023/24", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)) {
                Text(text = "VIEW ALL UPCOMING GAMES", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CountdownTimer(days: Int, hours: Int, minutes: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TimeUnit(value = days, unit = "DAYS")
        Spacer(modifier = Modifier.width(8.dp))
        TimeUnit(value = hours, unit = "HRS")
        Spacer(modifier = Modifier.width(8.dp))
        TimeUnit(value = minutes, unit = "MIN")
    }
}

@Composable
fun TimeUnit(value: Int, unit: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$value", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text(text = unit, color = Color.Gray, fontSize = 12.sp)
    }
}