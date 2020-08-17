package com.example.compse_ui.ui

import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.material.AlertDialog
import androidx.ui.material.FilledTextField
import androidx.ui.material.TextButton
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.data.Score
import androidx.compose.getValue
import androidx.compose.onActive
import androidx.compose.setValue
import androidx.ui.core.focus.FocusModifier
import androidx.ui.foundation.TextFieldValue
import androidx.ui.input.KeyboardType
import androidx.ui.text.TextRange

@Composable
fun ScoreDialog(
    onCloseRequest: () -> Unit,
    score: Score,
    onConfirm: (newValue: Int) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var scoreValue by savedInstanceState(saver = TextFieldValue.Saver) {
        val text = score.value.toString()
        TextFieldValue(text, TextRange(0, text.length))
    }

    AlertDialog(
        onCloseRequest = onCloseRequest,
        title = { Text("Redigera poäng") },
        text = {
            val focusModifier = FocusModifier()
            onActive {
                focusModifier.requestFocus()
            }
            FilledTextField(
                modifier = focusModifier,
                keyboardType = KeyboardType . Number,
                value = scoreValue,
                onValueChange = { scoreValue = it },
                label = { }
            )
        },
        confirmButton = {
            TextButton(
                enabled = scoreValue.text.toIntOrNull() != null,
                onClick = { onConfirm(scoreValue.text.toInt()) }
            ) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Avbryt")
            }
        },
    )
}


@Composable
@Preview(showBackground = true, widthDp = 400, heightDp = 700)
fun ScoreDialogPreview() {
    ScoreisTheme {
        ScoreDialog( {}, Score(16, 0) )
    }
}