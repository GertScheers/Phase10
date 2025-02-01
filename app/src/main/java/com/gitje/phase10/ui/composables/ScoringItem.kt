package com.gitje.phase10.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.Phase10Theme
import com.gitje.phase10.R
import com.gitje.phase10.models.Player

@Composable
fun ScoringItem(player: Player, confirmResults: Boolean, modifier: Modifier = Modifier, saveItem: (Int, Boolean) -> Unit) {
    var penaltyPoints by remember { mutableStateOf("") }
    var clearedStage by remember { mutableStateOf(false) }
    var resultsSaved by remember { mutableStateOf(false) }

    if(confirmResults && !resultsSaved) {
        resultsSaved = true
        saveItem(penaltyPoints.trim().ifEmpty { "0" }.toInt(), clearedStage)
    }

    OutlinedCard(
        modifier = modifier
            .height(dimensionResource(id = com.intuit.sdp.R.dimen._180sdp))
            .width(dimensionResource(id = com.intuit.sdp.R.dimen._100sdp)),
        shape = RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._15sdp))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = player.name, textAlign = TextAlign.Center, fontSize = scalableFontSize(id = com.intuit.ssp.R.dimen._18ssp))
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
                clearedStage = !clearedStage
            }.focusable(false)) {
                Text("Stage cleared?")
                Checkbox(
                    checked = clearedStage,
                    onCheckedChange = { clearedStage = it },
                    modifier = Modifier.size(dimensionResource(id = com.intuit.sdp.R.dimen._40sdp))
                )
            }
            TextField(
                value = penaltyPoints,
                onValueChange = { penaltyPoints = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                label = { Text("Penalty points") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoringItemPreview() {
    Phase10Theme {
        ScoringItem(Player("HELLO"), false) { _, _ -> }
    }
}