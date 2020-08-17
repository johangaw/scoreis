package com.example.compse_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.Game
import com.example.compse_ui.data.getSampleGame
import com.example.compse_ui.ui.AppLayout
import com.example.compse_ui.ui.ScoreTable
import com.example.compse_ui.ui.ScoreisTheme


class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScoreisTheme {
                AppLayout {
                    ScoreTable(game = getSampleGame())
                }
            }
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

