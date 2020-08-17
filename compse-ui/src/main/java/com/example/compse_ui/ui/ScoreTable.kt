package com.example.compse_ui.ui

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.RowScope.weight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.Game
import com.example.compse_ui.data.getSampleGame

@Composable
fun ScoreTable(game: Game) {
    Column(Modifier.fillMaxWidth().weight(1.0F)) {
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

        // maxOrNull does not work with the preview...
        // Maybe need to upgrade something to kotlin 1.4
        val rounds = game.players.map { it.scores }.map { it.size }.max() ?: 0

        repeat(rounds) { index ->
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