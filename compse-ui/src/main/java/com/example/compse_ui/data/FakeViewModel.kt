package com.example.compse_ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random


interface IScoreFragmentViewModel {
    val gameState: LiveData<Game?>
    fun addPlayer(names: List<String>)
    fun addScore(playerAndScores: List<Pair<Player, Int>>)
    fun fillScores(defaultScore: Int = 0)
    fun editScoreValue(score: Score, newValue: Int)
}


class InMemoryScoreFragmentViewModel : ViewModel(), IScoreFragmentViewModel {

    private val _gameState = MutableLiveData(getSimpleGame())
    override val gameState: LiveData<Game?> = _gameState

    private val game: Game get() = _gameState.value!!

    override fun addPlayer(names: List<String>) {
        _gameState.postValue(
            game.copy(
                players = game.players + names.map { Player(it, emptyList(), Random.nextInt()) }
            )
        )
    }

    override fun addScore(playerAndScores: List<Pair<Player, Int>>) {
        _gameState.postValue(
            game.copy(
                players = game.players.map {player ->
                    player.copy(
                        scores = player.scores + playerAndScores
                            .filter { (p, _) -> p == player }
                            .map { (_, score) -> Score(score, Random.nextInt()) }
                    )
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

    override fun editScoreValue(score: Score, newValue: Int) {
        _gameState.postValue(
            game.copy(
                players = game.players.map { player ->
                    player.copy(
                        scores = player.scores.map {
                            if(it == score) score.copy(value = newValue) else it
                        }
                    )
                }
            )
        )
    }


}