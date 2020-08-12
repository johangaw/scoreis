package com.example.scoreis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ScoreFragmentViewModel : ViewModel() {

    private val participants: MutableLiveData<LinkedHashMap<String, MutableList<Int>>> =
        MutableLiveData(
            linkedMapOf()
        )

    fun getParticipants(): LiveData<List<String>> =
        Transformations.map(participants) { it.keys.toList() }

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