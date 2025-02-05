package com.gitje.phase10.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.gitje.phase10.R
import com.gitje.phase10.ui.theme.Phase10Theme

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