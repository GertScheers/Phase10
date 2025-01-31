package com.gitje.phase10.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gitje.phase10.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players

    val stages = listOf(
        "AAA + BBB",
        "AAA + ABCD",
        "AAAA + ABCD",
        "ABCDEFG",
        "ABCDEFGH",
        "ABCDEFGHI",
        "AAAA + BBBB",
        "7 color",
        "AAAAA + BB",
        "AAAAA + BBB"
    )

    fun setPlayers(playerNames: List<String>) {
        val newPlayers = mutableListOf<Player>()
        playerNames.forEach {
            if(it.isEmpty()) return@forEach
            newPlayers.add(Player(it))
        }
        _players.value = newPlayers
    }
}