package com.example.compse_ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.setContent
import androidx.ui.livedata.observeAsState
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.*
import com.example.compse_ui.ui.AppLayout
import com.example.compse_ui.ui.ScoreTable
import com.example.compse_ui.ui.ScoreisTheme
import com.example.compse_ui.utils.getPlayers
import com.example.compse_ui.utils.getScoreFor
import com.example.compse_ui.ui.ScoreDialog
import java.util.*


class ComposeActivity : AppCompatActivity() {

    private val viewModel: IScoreFragmentViewModel by lazy {
        ViewModelProvider(this).get(InMemoryScoreFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent lambda@{
            val game = viewModel.gameState.observeAsState().value ?: return@lambda
            val (scoreToEdit, setScoreToEdit) = savedInstanceState<Score?> { null }

            App(
                onSetRestToZeroClick = { viewModel.fillScores(0) },
                onAddPlayerClick = { tryConvertSpeechToText(RecordingRequest.GET_PLAYERS) },
                onAddScoreClick = { tryConvertSpeechToText(RecordingRequest.GET_SCORE) },
                game = game,
                onScoreClickListener = setScoreToEdit,
                onPlayerClickListener = { }
            )
            if(scoreToEdit != null) {
                ScoreDialog(
                    onCloseRequest = { setScoreToEdit(null) },
                    score = scoreToEdit,
                    onConfirm = {
                        viewModel.editScoreValue(scoreToEdit, it)
                        setScoreToEdit(null)
                    }
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RecordingRequest.GET_PLAYERS.code -> {
                if (resultCode == RESULT_OK) {
                    data?.let {
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            ?.let {
                                newPlayers(it[0])
                            }
                    }
                }
            }
            RecordingRequest.GET_SCORE.code -> {
                if (resultCode == RESULT_OK) {
                    data?.let {
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            ?.let {
                                newScores(it[0])
                            }
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
                applicationContext,
                "Sorry your device not supported",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun newScores(newScoreSpeech: String) {
        viewModel.gameState.value?.let { game ->
            viewModel.addScore(
                game.players.mapNotNull { player ->
                    getScoreFor(newScoreSpeech, player.name)?.let {
                        Pair(
                            player,
                            it
                        )
                    }
                }
            )
        }
    }

    private fun newPlayers(newPlayerSpeech: String) {
        viewModel.addPlayer(getPlayers(newPlayerSpeech))
    }

    enum class RecordingRequest(val code: Int, val msg: String) {
        GET_PLAYERS(100, "Vilka är med?"),
        GET_SCORE(101, "Hur många poäng fick ni?")
    }
}

@Composable
fun App(
    onSetRestToZeroClick: () -> Unit,
    onAddPlayerClick: () -> Unit,
    onAddScoreClick: () -> Unit,
    game: Game,
    onPlayerClickListener: (player: Player) -> Unit,
    onScoreClickListener: (score: Score) -> Unit
) {
    ScoreisTheme {
        AppLayout(
            onSetRestToZeroClick,
            onAddPlayerClick,
            onAddScoreClick
        ) {
            ScoreTable(
                game = game,
                onPlayerClickListener = onPlayerClickListener,
                onScoreClickListener = onScoreClickListener,
            )
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App(
        {},
        {},
        {},
        game = getSampleGame(),
        onPlayerClickListener = {},
        onScoreClickListener = {}
    )
}

