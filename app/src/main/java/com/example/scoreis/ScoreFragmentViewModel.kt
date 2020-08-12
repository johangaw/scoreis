package com.example.scoreis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.scoreis.data.Game
import com.example.scoreis.data.Player
import com.example.scoreis.data.Score

class ScoreFragmentViewModel : ViewModel() {

    private val scoreMap: MutableLiveData<LinkedHashMap<String, MutableList<Int>>> =
        MutableLiveData(
            linkedMapOf()
        )

    fun getGame(): LiveData<Game> = Transformations.map(scoreMap) { scoreMap ->
        val players = scoreMap.entries.map {(name, scores) ->
            Player(name = name, scores = scores.map { Score(it) })
        }
        Game(players = players)
    }

    fun addPlayer(names: List<String>) {
        names.forEach { name ->
            scoreMap.value?.putIfAbsent(name, mutableListOf())
        }
        scoreMap.postValue(scoreMap.value)
    }

    fun addScore(player: String, score: Int) {
        scoreMap.value?.get(player)?.add(score)
        scoreMap.postValue(scoreMap.value)
    }

    fun fillScores(defaultScore: Int = 0) {
        scoreMap.value?.let { scoreMap ->
            val maxNumberOfScores = scoreMap.values.map { it.size }.max() ?: 0
            val defaultSequence = generateSequence { defaultScore }
            scoreMap.values.forEach {scores ->
                scores.addAll(defaultSequence.take(maxNumberOfScores - scores.size))
            }
            this.scoreMap.postValue(scoreMap)
        }
    }
}