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
import com.example.scoreis.data.Game
import com.example.scoreis.databinding.FragmentScoreBinding
import com.example.scoreis.utils.Logger
import com.example.scoreis.utils.ScoreMatrixIterator
import com.example.scoreis.utils.getPlayers
import com.example.scoreis.utils.getScoreFor
import java.util.*


class ScoreFragment : Fragment(), Logger {

    private val viewModel: ScoreFragmentViewModel by viewModels()
    private var game: Game? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScoreBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            tryConvertSpeechToText(RecordingRequest.GET_SCORE)
        }

        binding.bottomAppBar.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_item_set_rest_to_zero -> {
                    setRestToZero()
                    true
                }
                R.id.menu_item_add_participants -> {
                    tryConvertSpeechToText(RecordingRequest.GET_PLAYERS)
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

        viewModel.getGame().observe(viewLifecycleOwner) { game ->
            this.game = game
            if (game.hasPlayers) layoutManager.spanCount = game.playerCount
            adapter.submitList(ScoreMatrixIterator(game).asSequence().toList())
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RecordingRequest.GET_PLAYERS.code -> {
                if (resultCode == RESULT_OK) {
                    data?.let {
                        val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        newPlayers(result[0])
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

    private fun newPlayers(names: String) {
        viewModel.addPlayer(getPlayers(names))
    }

    private fun newScores(scores: String) {
        game?.players
            ?.forEach { player ->
                getScoreFor(scores, player.name)?.let { scoreValue ->
                    viewModel.addScore(player.name, scoreValue)
                }
            }
    }

    private fun setRestToZero() {
        viewModel.fillScores(0)
    }

    enum class RecordingRequest(val code: Int, val msg: String) {
        GET_PLAYERS(100, "Vilka är med?"),
        GET_SCORE(101, "Hur många poäng fick ni?")
    }

}
