package com.gitje.phase10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gitje.phase10.ui.composables.GameScreen
import com.gitje.phase10.ui.composables.HomeScreen
import com.gitje.phase10.ui.composables.PlayerEntryScreen
import com.gitje.phase10.ui.composables.WinnerScreen
import com.gitje.phase10.ui.composables.ScoringScreen
import com.gitje.phase10.ui.theme.Phase10Theme
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

