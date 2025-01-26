package com.gitje.phase10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gitje.phase10.models.Player
import com.gitje.phase10.ui.composables.PlayerItem
import com.gitje.phase10.ui.composables.ScoringItem
import com.gitje.phase10.ui.theme.Phase10Theme
import com.gitje.phase10.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Phase10Theme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val viewModel: MainViewModel = koinViewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = HomeScreen) {
                        composable<HomeScreen> {
                            HomeScreen(
                                { navController.navigate(route = PlayerEntryScreen) },
                                { /*TODO: DISPLAY GAME RULES */ }
                            )
                        }
                        composable<PlayerEntryScreen> {
                            PlayerEntryScreen {
                                viewModel.setPlayers(it)
                                navController.navigate(
                                    route = GameScreen
                                )
                            }
                        }
                        composable<GameScreen> {
                            GameScreen(viewModel.getPlayers(), viewModel.stages) {
                                navController.navigate(route = ScoringScreen)
                            }
                        }
                        composable<ScoringScreen> { 
                            ScoringScreen(players = viewModel.getPlayers()) {
                                navController.navigate(route = GameScreen)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object PlayerEntryScreen

@Serializable
object GameScreen

@Serializable
object ScoringScreen

@Composable
fun HomeScreen(startNewGame: () -> Unit, gameRules: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { startNewGame() }) {
            Text("New Game")
        }

        Button(onClick = { gameRules() }) {
            Text("Game rules")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Phase10Theme {
        HomeScreen({}, {})
    }
}

@Composable
fun PlayerEntryScreen(startGame: (List<String>) -> Unit) {
    var player1 by remember { mutableStateOf("") }
    var player2 by remember { mutableStateOf("") }
    var player3 by remember { mutableStateOf("") }
    var player4 by remember { mutableStateOf("") }
    var player5 by remember { mutableStateOf("") }
    var player6 by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = player1, onValueChange = { player1 = it }, label = { Text("Player 1") })
        TextField(value = player2, onValueChange = { player2 = it }, label = { Text("Player 2") })
        if (player1.isNotEmpty() && player2.isNotEmpty())
            TextField(
                value = player3,
                onValueChange = { player3 = it },
                label = { Text("Player 3") })
        if (player3.isNotEmpty())
            TextField(
                value = player4,
                onValueChange = { player4 = it },
                label = { Text("Player 4") })
        if (player4.isNotEmpty())
            TextField(
                value = player5,
                onValueChange = { player5 = it },
                label = { Text("Player 5") })
        if (player5.isNotEmpty())
            TextField(
                value = player6,
                onValueChange = { player6 = it },
                label = { Text("Player 6") })

        Button(onClick = {
            startGame(listOf(player1, player2, player3, player4, player5, player6))
        }, modifier = Modifier.padding(top = 20.dp)) {
            Text("Start!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerEntryScreenPreview() {
    Phase10Theme {
        PlayerEntryScreen {}
    }
}

@Composable
fun GameScreen(players: List<Player>, stages: List<String>, finished: (String) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(players) {
            PlayerItem(player = it, stages) { winnerKey ->
                finished(winnerKey)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    Phase10Theme {
        GameScreen(
            listOf(Player("P1"), Player("P2").apply { this.stage = 2 }, Player("P3")),
            listOf("AAA + BBB", "ABCDEFG")
        ) { }
    }
}

@Composable
fun ScoringScreen(players: List<Player>, backToGameScreen: () -> Unit) {
    val pagerState = rememberPagerState(0, pageCount = { players.size })
    var confirmResults by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.End) {
        HorizontalPager(
            state = pagerState,
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentPadding = PaddingValues(start = 80.dp, end = 50.dp)
        ) {
            ScoringItem(player = players[it], confirmResults)
        }

        if (pagerState.currentPage == players.size - 1)
            Button(onClick = {
                confirmResults = true
            }) {
                Text(text = "Confirm")
            }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoringScreenPreview() {
    Phase10Theme {
        ScoringScreen(
            listOf(
                Player("PLAYER 1"),
                Player("PLAYER 2"),
                Player("PLAYER 3"),
                Player("PLAYER 4")
            )
        ) { }
    }
}