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
            when(item.itemId) {
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

        viewModel.getParticipants().observe(viewLifecycleOwner) {participants ->
            adapter.submitList(participants)
            layoutManager.spanCount = max(participants.size, 1)
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
        val noNames = listOf("och")
        val nameList = names
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .filter { !noNames.contains(it) }
            .toList()
        viewModel.addParticipants(nameList)
    }

    enum class RecordingRequest(val code: Int, val msg: String) {
        GET_PARTICIPANTS(100, "Vilka är med?"),
        GET_SCORE(101, "Hur många poäng fick ni?")
    }

}


