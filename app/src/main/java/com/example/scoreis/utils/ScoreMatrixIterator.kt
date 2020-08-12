package com.example.scoreis.utils

import com.example.scoreis.data.Game

class ScoreMatrixIterator(private val game: Game) : Iterator<String> {
    var col = 0
    var row = 0

    override fun hasNext(): Boolean {
        if(!game.hasPlayers) return false
        return row < game.startedRounds + 1
    }

    override fun next(): String {
        return when (row) {
            0 -> {
                game.players[col]
                    .let { it.name }
                    .also { incrementCol() }
            }
            else -> {
                val scores = game.players[col].scores
                (scores.getOrNull(row - 1)?.let { it.value.toString() } ?: "")
                    .also { incrementCol() }
            }
        }
    }

    private fun incrementCol() {
        col = (col + 1) % game.playerCount
        if (col == 0) ++row
    }
}