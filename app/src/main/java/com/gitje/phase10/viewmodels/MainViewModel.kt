package com.gitje.phase10.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gitje.phase10.models.Player

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var players = mutableListOf<Player>()

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
        players = mutableListOf()
        playerNames.reversed().forEach {
            if (it.isEmpty()) return
            players.add(Player(it))
        }
    }

    fun getPlayers(): List<Player> {
        return players
    }
}