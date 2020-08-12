package com.example.scoreis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreFragmentViewModel: ViewModel() {

    private val participants: MutableLiveData<List<String>> = MutableLiveData(emptyList())

    fun getParticipants(): LiveData<List<String>> = participants

    fun addParticipants(names: List<String>) {
        val newParticipants = participants.value!! + names
        participants.postValue(newParticipants.distinct())
    }
}