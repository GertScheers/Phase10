package com.gitje.phase10.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.gitje.phase10.models.Player
import com.gitje.phase10.ui.theme.Phase10Theme
import com.intuit.sdp.R


@Composable
fun GameScreen(players: List<Player>, stages: List<String>, finished: (String) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen._3sdp)), verticalArrangement = Arrangement.Center) {
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