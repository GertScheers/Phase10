package com.gitje.phase10.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitje.phase10.models.Player
import com.gitje.phase10.ui.theme.Phase10Theme

@Composable
fun PlayerItem(player: Player, stages: List<String>, finished: (String) -> Unit) {
    OutlinedCard(shape = RectangleShape, elevation = CardDefaults.outlinedCardElevation(5.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = player.name)
            Text(text = player.points.toString())
            Button(onClick = { finished(player.key) }) {
                Text(text = "Finish")
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.Center) {
            Text(text = "${player.stage}. ")
            Text(text = stages[player.stage - 1])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerItemPreview() {
    Phase10Theme {
        PlayerItem(Player("The best"), listOf("AAAAA+ AAAAAAA")) { }
    }
}