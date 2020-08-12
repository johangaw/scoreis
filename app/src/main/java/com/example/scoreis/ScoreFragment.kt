package com.example.scoreis

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.scoreis.databinding.FragmentScoreBinding
import com.example.scoreis.utils.Logger
import com.example.scoreis.utils.getParticipants
import com.example.scoreis.utils.getScoreFor
import java.lang.Integer.max
import java.util.*


class ScoreFragment : Fragment(), Logger {

    private val viewModel: ScoreFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScoreBinding.inflate(inflater, container, false)

        binding.bottomAppBar.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_item_add_score -> {
                    tryConvertSpeechToText(RecordingRequest.GET_SCORE)
                    true
                }
                R.id.menu_item_add_participants -> {
                    tryConvertSpeechToText(RecordingRequest.GET_PARTICIPANTS)
                    true
                }
                else -> false
            }
        }

        val layoutManager = GridLayoutManager(context, 1)
        val adapter = ScoreGridAdapter()
        binding.scoreGridLayout.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }

        viewModel.getScorePerParticipant().observe(viewLifecycleOwner) { scoreMap ->
            val scores = scoreMap.values
            layoutManager.spanCount = max(scoreMap.keys.size, 1)
            adapter.submitList(scoreMap.keys.toList() + zipAll(scores.toList()).map { it.toString()})
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RecordingRequest.GET_PARTICIPANTS.code -> {
                if (resultCode == RESULT_OK) {
                    data?.let {
                        val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        newParticipants(result[0])
                    }
                }
            }
            RecordingRequest.GET_SCORE.code -> {
                if (resultCode == RESULT_OK) {
                    data?.let {
                        val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        newScores(result[0])
                    }
                }
            }
        }
    }

    private fun tryConvertSpeechToText(request: RecordingRequest) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("sv_SE"))
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak")
        try {
            startActivityForResult(intent, request.code)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                requireActivity().applicationContext,
                "Sorry your device not supported",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun newParticipants(names: String) {
        viewModel.addParticipants(getParticipants(names))
    }

    private fun newScores(scores: String) {
        val participants = viewModel.getScorePerParticipant().value?.keys?.toList() ?: emptyList()
        participants.forEach { participant ->
            getScoreFor(scores, participant)?.let { score ->
                viewModel.addScore(participant, score)
            }
        }
    }

    enum class RecordingRequest(val code: Int, val msg: String) {
        GET_PARTICIPANTS(100, "Vilka är med?"),
        GET_SCORE(101, "Hur många poäng fick ni?")
    }

}


fun zipAll(list: List<List<Int>>): List<Int> {
    return when (list.size) {
        0 -> emptyList()
        1 -> list.first()
        else -> {
            val head = list.first()
            val tail = list.drop(1)
            head.zip(zipAll(tail)) { a, b -> listOf(a, b) }.flatten()
        }
    }
}

