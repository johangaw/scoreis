package com.example.compse_ui.data

import kotlin.random.Random

fun getNewGame(): Game {
    return Game(
        gameId = Random.nextInt(),
        players = listOf(
            Player(
                name = "Elin",
                scores = emptyList(),
                id = Random.nextInt()
            ),

            Player(
                name = "Johan",
                scores = emptyList(),
                id = Random.nextInt()
            ),

            Player(
                name = "Helena",
                scores = emptyList(),
                id = Random.nextInt()
            ),

            Player(
                name = "Jonte",
                scores = emptyList(),
                id = Random.nextInt()
            ),
        )
    )
}

fun getSimpleGame(): Game {
    return Game(
        gameId = Random.nextInt(),
        players = listOf(
            Player(
                name = "Elin",
                scores = listOf(
                    Score(13, Random.nextInt())
                ),
                id = Random.nextInt()
            ),

            Player(
                name = "Johan",
                scores = emptyList(),
                id = Random.nextInt()
            ),

            Player(
                name = "Helena",
                scores = listOf(
                    Score(55, Random.nextInt())
                ),
                id = Random.nextInt()
            ),

            Player(
                name = "Jonte",
                scores = emptyList(),
                id = Random.nextInt()
            ),
        )
    )
}

fun getSampleGame(): Game {
    return Game(
        gameId = Random.nextInt(),
        players = listOf(
            Player(
                name = "Elin",
                scores = generateSequence { Random.nextInt(0, 100) }.take(5)
                    .map { Score(it, Random.nextInt()) }.toList(),
                id = Random.nextInt()
            ),

            Player(
                name = "Johan",
                scores = generateSequence { Random.nextInt(0, 100) }.take(3)
                    .map { Score(it, Random.nextInt()) }.toList(),
                id = Random.nextInt()
            ),

            Player(
                name = "Helena",
                scores = generateSequence { Random.nextInt(0, 100) }.take(6)
                    .map { Score(it, Random.nextInt()) }.toList(),
                id = Random.nextInt()
            ),

            Player(
                name = "Jonte",
                scores = generateSequence { Random.nextInt(0, 100) }.take(2)
                    .map { Score(it, Random.nextInt()) }.toList(),
                id = Random.nextInt()
            ),
        )
    )
}