package com.gitje.phase10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gitje.phase10.ui.theme.Phase10Theme
import com.gitje.phase10.models.Player
import com.gitje.phase10.ui.composables.PlayerItem
import com.gitje.phase10.ui.composables.ScoringItem
import com.gitje.phase10.ui.composables.animatedBorder
import com.gitje.phase10.viewmodels.MainViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import timber.log.Timber.Forest.plant


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plant(Timber.DebugTree())

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
                            val players by viewModel.players.collectAsState()
                            GameScreen(players, viewModel.stages) {
                                navController.navigate(route = ScoringScreen(it))
                            }
                        }
                        composable<ScoringScreen> {
                            val players by viewModel.players.collectAsState()
                            val route: ScoringScreen = it.toRoute()
                            val playersForScoring = players.toMutableList()
                            playersForScoring.removeIf { p -> p.key == route.playerKey }

                            ScoringScreen(players = playersForScoring)
                            {
                                players.firstOrNull { p -> p.key == route.playerKey }
                                    ?.let { player -> player.stage++ }
                                if (players.any { pl -> pl.stage > 10 }) {
                                    navController.navigate(route = WinnerScreen)
                                } else navController.navigate(route = GameScreen)
                            }
                        }
                        composable<WinnerScreen> {
                            val players by viewModel.players.collectAsState()
                            WinnerScreen(players) {
                                navController.navigate(route = HomeScreen)
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
data class ScoringScreen(val playerKey: String)

@Serializable
object WinnerScreen

@Composable
fun HomeScreen(startNewGame: () -> Unit, gameRules: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_header),
            contentDescription = "logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            Button(onClick = { startNewGame() }, modifier = Modifier.padding(vertical = dimensionResource(
                id = com.intuit.sdp.R.dimen._5sdp
            ))) {
                Text(stringResource(id = R.string.btn_new_game))
            }

            Button(onClick = { gameRules() }, modifier = Modifier.padding(vertical = dimensionResource(
                id = com.intuit.sdp.R.dimen._5sdp
            ))) {
                Text(stringResource(id = R.string.btn_game_rules))
            }
        }

        Image(
            painter = painterResource(id = R.drawable.home_footer),
            contentDescription = "logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
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
        TextField(
            value = player1,
            onValueChange = { player1 = it },
            label = { Text(stringResource(id = R.string.label_player_number, "1")) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            value = player2,
            onValueChange = { player2 = it },
            label = { Text(stringResource(id = R.string.label_player_number, "2")) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (player1.isNotEmpty() && player2.isNotEmpty())
            TextField(
                value = player3,
                onValueChange = { player3 = it },
                label = { Text(stringResource(id = R.string.label_player_number, "3")) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
        if (player3.isNotEmpty())
            TextField(
                value = player4,
                onValueChange = { player4 = it },
                label = { Text(stringResource(id = R.string.label_player_number, "4")) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
        if (player4.isNotEmpty())
            TextField(
                value = player5,
                onValueChange = { player5 = it },
                label = { Text(stringResource(id = R.string.label_player_number, "5")) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
        if (player5.isNotEmpty())
            TextField(
                value = player6,
                onValueChange = { player6 = it },
                label = { Text(stringResource(id = R.string.label_player_number, "6")) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

        Button(
            onClick = {
                startGame(listOf(player1, player2, player3, player4, player5, player6))
            },
            modifier = Modifier.padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._15sdp))
        ) {
            Text(stringResource(id = R.string.btn_start))
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
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)), verticalArrangement = Arrangement.Center) {
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
    //val pagerState = rememberPagerState(0, pageCount = { players.size })
    var confirmResults by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
            itemsIndexed(players) { index, player ->
                ScoringItem(
                    player = player,
                    confirmResults,
                    modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._7sdp))
                ) { points, clearedStage ->
                    player.points += points
                    if (clearedStage && player.stage < 10)
                        player.stage++
                    if (index == players.size - 1)
                        backToGameScreen()
                }
            }
        }

        Button(
            onClick = {
                confirmResults = true
            },
            modifier = Modifier.padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._20sdp))
        ) {
            Text(text = stringResource(id = R.string.btn_confirm))
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

@Composable
fun WinnerScreen(players: List<Player>, backToStart: () -> Unit) {
    val winner = players.filter { p -> p.stage == 11 }
        .minBy { p -> p.points }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._120sdp))
                    .animatedBorder(
                        listOf(
                            Color.Black,
                            Color.Yellow,
                            Color.White,
                            Color.Green,
                            Color.Blue,
                            Color.Red
                        ),
                        Color.White,
                        CircleShape,
                        dimensionResource(id = com.intuit.sdp.R.dimen._2sdp),
                        animationDurationInMillis = 3000
                    ),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "10",
                        fontSize = TextUnit(48f, type = TextUnitType.Sp),
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }
            Text(
                text = winner.name.uppercase(),
                textAlign = TextAlign.Center,
                fontSize = TextUnit(48f, TextUnitType.Sp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var position = 2
            players.sortedWith(compareBy({ -it.stage }, { it.points })).forEach { p ->
                if (p.key == winner.key) return@forEach
                Text(
                    text = stringResource(id = R.string.label_player_score, position, p.name, p.stage, p.points),
                    fontSize = TextUnit(24f, TextUnitType.Sp)
                )
                position++
            }
        }
        Button(onClick = { backToStart() }) {
            Text(text = stringResource(id = R.string.btn_restart))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinnerScreenPreview() {
    Phase10Theme {
        WinnerScreen(
            listOf(Player("Player 1"), Player("Player 2").apply {
                this.stage = 6
                this.points = 220
            },
                Player("Player 3").apply {
                    this.stage = 9
                    this.points = 320
                },
                Player("Player 4").apply {
                    this.stage = 6
                    this.points = 200
                },
                Player("Player 5").apply {
                    this.stage = 10
                    this.points = 200
                })
        ) { }
    }
}