package com.gitje.phase10.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.gitje.phase10.models.Player
import com.gitje.phase10.ui.theme.Phase10Theme
import com.intuit.sdp.R

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
                    .size(dimensionResource(id = R.dimen._120sdp))
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
                        dimensionResource(id = R.dimen._2sdp),
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
                    text = stringResource(id = com.gitje.phase10.R.string.label_player_score, position, p.name, p.stage, p.points),
                    fontSize = TextUnit(24f, TextUnitType.Sp)
                )
                position++
            }
        }
        Button(onClick = { backToStart() }) {
            Text(text = stringResource(id = com.gitje.phase10.R.string.btn_restart))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinnerScreenPreview() {
    Phase10Theme {
        WinnerScreen(
            listOf(
                Player("Player 1"), Player("Player 2").apply {
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