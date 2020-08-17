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
import androidx.ui.material.TextButton
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.Game
import com.example.compse_ui.data.Player
import com.example.compse_ui.data.Score
import com.example.compse_ui.data.getSampleGame

@Composable
fun ScoreTable(
    game: Game,
    onScoreClickListener: (score: Score) -> Unit = {},
    onPlayerClickListener: (player: Player) -> Unit = {},
) {
    Column(Modifier.fillMaxWidth().weight(1.0F)) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            game.players.forEach {
                TextButton(
                    modifier = Modifier.weight(1.0F),
                    onClick = { onPlayerClickListener(it) }
                ) {
                    Text(
                        text = it.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                    )
                }
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
                for (player in game.players) {
                    val score = player.scores.getOrNull(index)
                    TextButton(
                        modifier = Modifier.weight(1.0F),
                        onClick = { score?.let { onScoreClickListener(score) } }
                    ) {
                        Text(
                            text = score?.value?.toString() ?: "",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun ScoreTablePreview() {
    ScoreisTheme {
        ScoreTable(
            game = getSampleGame(),
            onScoreClickListener = {},
            onPlayerClickListener = {}
        )
    }
}