package com.example.scoreis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreFragmentViewModel : ViewModel() {

    private val participants: MutableLiveData<LinkedHashMap<String, MutableList<Int>>> =
        MutableLiveData(
            linkedMapOf()
        )

    @Suppress("UNCHECKED_CAST")
    fun getScorePerParticipant(): LiveData<Map<String, List<Int>>> =
        participants as LiveData<Map<String, List<Int>>>

    fun addParticipants(names: List<String>) {
        names.forEach { name ->
            participants.value?.putIfAbsent(name, mutableListOf())
        }
        participants.postValue(participants.value)
    }

    fun addScore(participant: String, score: Int) {
        participants.value?.get(participant)?.add(score)
        participants.postValue(participants.value)
    }
}