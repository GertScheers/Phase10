package com.gitje.phase10.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.Phase10Theme
import com.gitje.phase10.models.Player

@Composable
fun PlayerItem(player: Player, stages: List<String>, finished: (String) -> Unit) {
    OutlinedCard(
        shape = RectangleShape,
        elevation = CardDefaults.outlinedCardElevation(dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = com.intuit.sdp.R.dimen._3sdp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = player.name)
            Row {
                Text(text = "Points:")
                Text(text = player.points.toString())
            }
            Button(onClick = { finished(player.key) }) {
                Text(text = "Finish")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${player.stage}. ")
            if (player.stage == 7) {
                Row {
                    ColorCard(Color.Red, modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)))
                    ColorCard(Color.Red, modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)))
                    ColorCard(Color.Red, modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)))
                    ColorCard(Color.Red, modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)))
                    ColorCard(Color.Red, modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)))
                    ColorCard(Color.Red, modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)))
                }
            } else {
                stages[player.stage - 1].forEach {
                    if (it == '+')
                        Text(text = "+")
                    else
                        CardText(character = it, Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)))
                }
            }
        }
    }
}

@Composable
fun CardText(character: Char, modifier: Modifier = Modifier) {
    Text(
        text = character.toString(), modifier = modifier
            .background(Color.White)
            .padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp))
    )
}

@Composable
fun ColorCard(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .background(color)
        .width(dimensionResource(id = com.intuit.sdp.R.dimen._7sdp))
        .height(dimensionResource(id = com.intuit.sdp.R.dimen._12sdp)))
}

@Preview(showBackground = true)
@Composable
fun PlayerItemPreview() {
    Phase10Theme {
        PlayerItem(Player("The best"), listOf("AAAAA+AAAAAAA")) { }
    }
}