package com.example.scoreis.utils

class ScoreMatrixIterator(private val scoreMap: Map<String, List<Int>>) : Iterator<String> {
    var col = 0
    var row = 0

    override fun hasNext(): Boolean {
        val maxScoreCount = scoreMap.values.map { it.size }.max() ?: 0
        val participantRows = if(scoreMap.isEmpty()) 0 else 1
        return row < maxScoreCount + participantRows
    }

    override fun next(): String {
        return when (row) {
            0 -> {
                scoreMap.keys
                    .toList()[col]
                    .also { incrementCol() }
            }
            else -> {
                val scores = scoreMap.values.toList()[col]
                (scores.getOrNull(row - 1)?.toString() ?: "")
                    .also { incrementCol() }
            }
        }
    }

    private fun incrementCol() {
        col = (col + 1) % scoreMap.keys.size
        if(col == 0) ++row
    }
}