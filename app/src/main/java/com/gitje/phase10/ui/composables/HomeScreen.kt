package com.gitje.phase10.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gitje.phase10.R
import com.gitje.phase10.ui.theme.Phase10Theme

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
            )
            )) {
                Text(stringResource(id = R.string.btn_new_game))
            }

            Button(onClick = { gameRules() }, modifier = Modifier.padding(vertical = dimensionResource(
                id = com.intuit.sdp.R.dimen._5sdp
            )
            )) {
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