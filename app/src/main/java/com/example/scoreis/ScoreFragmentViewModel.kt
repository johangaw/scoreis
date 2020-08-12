package com.example.scoreis

import android.app.Application
import androidx.lifecycle.*
import com.example.scoreis.data.Game
import com.example.scoreis.data.Player
import com.example.scoreis.database.*
import com.example.scoreis.utils.Logger
import com.example.scoreis.utils.asGame
import kotlinx.coroutines.launch

class ScoreFragmentViewModel(private val database: ScoreisDatabase) : ViewModel(), Logger {

    private val gameId = 1

    init {
        //TODO remove when game is selected/created from UI
        viewModelScope.launch {
            val game = database.gameDao.getGame(gameId)
            if (game == null) {
                database.gameDao.insert(DBGame(gameId))
            }
        }
    }

    val gameState: LiveData<Game?> = Transformations.map(database.gameDao.observeGame(gameId)) {
        it?.asGame()
    }

    fun addPlayer(names: List<String>) {
        viewModelScope.launch {
            database.playerDao.insert(
                names.map { DBPlayer(name = it, gameId = gameId) }
            )
        }
    }

    fun addScore(player: Player, score: Int) {
        viewModelScope.launch {
            database.scoreDao.insert(
                listOf(
                    DBScore(
                        playerId = player.id,
                        value = score
                    )
                )
            )
        }
    }

    fun fillScores(defaultScore: Int = 0) {
        viewModelScope.launch {
            database.gameDao.getGame(gameId)
                ?.asGame()
                ?.apply {
                    players.forEach { player ->
                        val defaultSequence = generateSequence { defaultScore }
                        database.scoreDao.insert(
                            defaultSequence
                                .take(startedRounds - player.scores.size)
                                .map { DBScore(value = it, playerId = player.id) }
                                .toList()
                        )
                    }
                }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ScoreFragmentViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreFragmentViewModel::class.java)) {
            val database = getDatabase(application)
            return ScoreFragmentViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}