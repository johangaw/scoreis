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