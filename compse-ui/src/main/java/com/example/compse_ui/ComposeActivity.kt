package com.example.compse_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.*
import com.example.compse_ui.data.getSampleGame
import com.example.compse_ui.ui.ScoreisTheme

class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScoreisTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ScoreTable(getSampleGame())
                }
            }
        }
    }
}

@Composable
fun ScoreTable(game: Game) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            game.players.forEach {
                Text(
                    text = it.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1.0F),
                    style = MaterialTheme.typography.h6,
                )
            }
        }

        val rounds = game.players.map { it.scores }.map { it.size }.max() ?: 0
        repeat(rounds) {index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (player in game.players) Text(
                    modifier = Modifier.weight(1.0F),
                    textAlign = TextAlign.Center,
                    text = player.scores.getOrNull(index)?.value?.toString() ?: "",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun ScoreTablePreview() {
    ScoreisTheme {
        ScoreTable(game = getSampleGame())
    }
}