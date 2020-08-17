package com.example.compse_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.setContent
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.Game
import com.example.compse_ui.data.IScoreFragmentViewModel
import com.example.compse_ui.data.InMemoryScoreFragmentViewModel
import com.example.compse_ui.data.getSampleGame
import com.example.compse_ui.ui.AppLayout
import com.example.compse_ui.ui.ScoreTable
import com.example.compse_ui.ui.ScoreisTheme
import kotlin.random.Random


class ComposeActivity : AppCompatActivity() {

    private val viewModel: IScoreFragmentViewModel by lazy {
        ViewModelProvider(this).get(InMemoryScoreFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent lambda@{
            val game = viewModel.gameState.observeAsState().value ?: return@lambda

            App(
                onSetRestToZeroClick = { viewModel.fillScores(0) },
                onAddScoreClick = {
                    viewModel.addScore(
                        game.players.first(),
                        Random.nextInt(0, 15)
                    )
                },
                onAddPlayerClick = {
                    val names =
                        listOf("Petter", "Markus", "Emil", "Magnus", "Albin", "Felix").shuffled()
                    viewModel.addPlayer(listOf(names.first()))
                },
                game = game ?: Game(emptyList(), 0),
            )
        }
    }
}

@Composable
fun App(
    onSetRestToZeroClick: () -> Unit,
    onAddPlayerClick: () -> Unit,
    onAddScoreClick: () -> Unit,
    game: Game
) {
    ScoreisTheme {
        AppLayout(
            onSetRestToZeroClick,
            onAddPlayerClick,
            onAddScoreClick
        ) {
            ScoreTable(game)
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App({}, {}, {}, game = getSampleGame())
}

