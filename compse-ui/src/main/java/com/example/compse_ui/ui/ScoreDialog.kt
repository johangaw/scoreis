package com.example.compse_ui.ui

import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.Score
import androidx.compose.getValue
import androidx.compose.onActive
import androidx.compose.setValue
import androidx.ui.core.Modifier
import androidx.ui.core.focus.FocusModifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.TextFieldValue
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Close
import androidx.ui.material.icons.filled.Delete
import androidx.ui.material.icons.filled.Done
import androidx.ui.text.TextRange
import androidx.ui.unit.dp

@Composable
fun ScoreDialog(
    score: Score,
    onConfirm: (newValue: Int) -> Unit = {},
    onDismiss: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    var scoreValue by savedInstanceState(saver = TextFieldValue.Saver) {
        val text = score.value.toString()
        TextFieldValue(text, TextRange(0, text.length))
    }

    AlertDialog(
        onCloseRequest = onDismiss,
        title = { Text("Redigera poäng") },
        text = {
            val focusModifier = FocusModifier()
            onActive {
                focusModifier.requestFocus()
            }
            FilledTextField(
                modifier = focusModifier,
                keyboardType = KeyboardType.Number,
                value = scoreValue,
                onValueChange = { scoreValue = it },
                label = { }
            )
        },
        buttons = {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(Icons.Default.Delete, tint = MaterialTheme.colors.error)
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(Icons.Default.Close)
                }
                IconButton(
                    onClick = { onConfirm(scoreValue.text.toInt()) }
                ) {
                    Icon(Icons.Default.Done, tint = MaterialTheme.colors.primary)
                }
            }
        }
    )
}


@Composable
@Preview(showBackground = true, widthDp = 400, heightDp = 700)
fun ScoreDialogPreview() {
    ScoreisTheme {
        ScoreDialog(Score(16, 0))
    }
}