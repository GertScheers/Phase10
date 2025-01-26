package com.gitje.phase10.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitje.phase10.models.Player
import com.gitje.phase10.ui.theme.Phase10Theme

@Composable
fun ScoringItem(player: Player, confirmResults: Boolean) {
    var penaltyPoints by remember { mutableStateOf("") }
    var clearedStage by remember { mutableStateOf(false) }

    if(confirmResults) {
        player.points += penaltyPoints.toInt()
        if(clearedStage)
            player.stage++
    }

    OutlinedCard(
        modifier = Modifier.size(height = 300.dp, width = 200.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = player.name, textAlign = TextAlign.Center)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Stage cleared?")
                Checkbox(
                    checked = clearedStage,
                    onCheckedChange = { clearedStage = it },
                    modifier = Modifier.size(40.dp)
                )
            }
            TextField(
                value = penaltyPoints,
                onValueChange = { penaltyPoints = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Penalty points") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoringItemPreview() {
    Phase10Theme {
        ScoringItem(Player("HELLO"), false)
    }
}