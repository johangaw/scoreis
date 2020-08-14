package com.example.compse_ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random


interface IScoreFragmentViewModel {
    val gameState: LiveData<Game?>
    fun addPlayer(names: List<String>)
    fun addScore(player: Player, score: Int)
    fun fillScores(defaultScore: Int = 0)
}


class InMemoryScoreFragmentViewModel : ViewModel(), IScoreFragmentViewModel {

    private val _gameState = MutableLiveData(Game(emptyList(), 0))
    override val gameState: LiveData<Game?> = _gameState

    private val game: Game get() = _gameState.value!!

    override fun addPlayer(names: List<String>) {
        _gameState.postValue(
            game.copy(
                players = game.players + names.map { Player(it, emptyList(), Random.nextInt()) }
            )
        )
    }

    override fun addScore(player: Player, score: Int) {
        _gameState.postValue(
            game.copy(
                players = game.players.map {
                    if (it == player) {
                        it.copy(scores = it.scores + listOf(Score(score, Random.nextInt())))
                    } else {
                        it
                    }
                }
            )
        )
    }

    override fun fillScores(defaultScore: Int) {
        _gameState.postValue(
            game.copy(
                players = game.players.map { player ->
                    val seq = generateSequence { defaultScore }
                    val diff = game.startedRounds - player.scores.size
                    player.copy(
                        scores = player.scores + seq.take(diff).map { Score(it, Random.nextInt()) }
                    )
                }
            )
        )
    }


}