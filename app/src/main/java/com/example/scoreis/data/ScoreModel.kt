package com.example.scoreis.data

data class Game(val players: List<Player>) {
    val playerCount: Int get() = players.size

    val startedRounds: Int get() = players.map { it.scores }.map { it.size }.max() ?: 0

    val hasPlayers: Boolean get() = players.isNotEmpty()
}

data class Player(val name: String, val scores: List<Score>)

data class Score(val value: Int)