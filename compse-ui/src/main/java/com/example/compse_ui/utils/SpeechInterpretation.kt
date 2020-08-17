package com.example.compse_ui.utils

fun getPlayers(speech: String): List<String> {
    val noNames = listOf("och")
    return speech
        .split(" ")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .filter { !noNames.contains(it) }
        .toList()
}

fun getScoreFor(speech: String, participant: String): Int? {
    val startIndex = speech.indexOf(participant)
    if (startIndex == -1) return null

    val matches = Regex("\\d+").find(speech, startIndex)
    return matches?.let { it.value.toInt() }
}