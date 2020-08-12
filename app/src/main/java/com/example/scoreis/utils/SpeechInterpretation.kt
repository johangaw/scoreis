package com.example.scoreis.utils

fun getParticipants(speech: String): List<String> {
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
    val matches = Regex("\\d+").find(speech, startIndex)
    return matches?.let { it.value.toInt() }
}