package com.example.scoreis.utils

import com.example.scoreis.data.Game
import com.example.scoreis.data.Player
import com.example.scoreis.data.Score
import com.example.scoreis.database.DBScore
import com.example.scoreis.database.GameWithPlayersWithScores
import com.example.scoreis.database.PlayerWithScores

fun GameWithPlayersWithScores.asGame(): Game {
    return Game(
        players = players.map { it.asPlayer() },
        gameId = game.id
    )
}

fun PlayerWithScores.asPlayer(): Player {
    return Player(
        id = player.id,
        name = player.name,
        scores = scores.map { it.asScore() }
    )
}

fun DBScore.asScore(): Score {
    return Score(
        id = id,
        value = value
    )
}