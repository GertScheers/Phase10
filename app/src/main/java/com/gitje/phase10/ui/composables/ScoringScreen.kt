package com.gitje.phase10.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gitje.phase10.models.Player
import com.gitje.phase10.ui.theme.Phase10Theme
import com.intuit.sdp.R

@Composable
fun ScoringScreen(players: List<Player>, backToGameScreen: () -> Unit) {
    var confirmResults by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
            itemsIndexed(players) { index, player ->
                ScoringItem(
                    player = player,
                    confirmResults,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen._7sdp))
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
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen._20sdp))
        ) {
            Text(text = stringResource(id = com.gitje.phase10.R.string.btn_confirm))
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