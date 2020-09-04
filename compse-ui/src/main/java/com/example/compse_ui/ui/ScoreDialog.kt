package com.example.compse_ui.ui

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.onActive
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Devices
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.Score


@ExperimentalFocus
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
        onDismissRequest = onDismiss,
        title = { Text("Redigera poäng") },
        text = {

            val focusRequester = FocusRequester()
            onActive {
                focusRequester.requestFocus()
            }

            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = scoreValue,
                onValueChange = {
                    scoreValue = it
                },
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


@ExperimentalFocus
@Composable
@Preview(showBackground = true, device = Devices.PIXEL_3)
fun ScoreDialogPreview() {
    ScoreisTheme {
        ScoreDialog(Score(16, 0))
    }
}