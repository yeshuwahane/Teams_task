package com.yeshuwahane.scores.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yeshuwahane.scores.presentation.games.GameViewModel
import com.yeshuwahane.scores.presentation.games.GamesScreen
import com.yeshuwahane.scores.presentation.schedule.ScheduleScreen
import com.yeshuwahane.scores.presentation.theme.ScoresTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel:GameViewModel by viewModels()


    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        val tabItems = listOf(
            TabItem(
                title = "Schedule",
            ),
            TabItem(
                title = "Games",
            )
        )



        setContent {




            ScoresTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedTabIndex by remember {
                        mutableIntStateOf(0)
                    }
                    val pagerState = rememberPagerState {
                        tabItems.size
                    }
                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }
                    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                        if (!pagerState.isScrollInProgress) {
                            selectedTabIndex = pagerState.currentPage
                        }
                    }

                    Scaffold(
                        
                        topBar = {
                            CenterAlignedTopAppBar(title = {
                                Text(
                                    text = "TEAM",
                                    fontWeight = FontWeight.ExtraBold,
                                    textAlign = TextAlign.Center,
                                    fontStyle = FontStyle.Italic
                                )
                            })
                        }

                    ) { paddingValues->

                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)) {
                            TabRow(selectedTabIndex = selectedTabIndex) {
                                tabItems.forEachIndexed { index, tabItem ->
                                    Tab(selected = index == selectedTabIndex, onClick = {
                                        selectedTabIndex = index
                                    },
                                        text = {
                                            Text(text = tabItem.title, color = Color.White)
                                        }
                                    )
                                }
                            }

                            HorizontalPager(
                                state = pagerState, modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                            ) { index ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (index == 0) {
                                        ScheduleScreen()

                                    } else {
                                        GamesScreen()
                                    }
                                }
                            }


                        }

                    }


                }
            }
        }
    }
}



data class TabItem(
    val title: String,
)






















