package com.example.compse_ui.ui

import androidx.compose.animation.animate
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.compse_ui.R
import kotlin.math.roundToInt


@Composable
fun AppLayout(
    onSetRestToZeroClick: () -> Unit = {},
    onAddPlayerClick: () -> Unit = {},
    onAddScoreClick: () -> Unit = {},
    content: @Composable (InnerPadding) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val fabShape = CircleShape

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = { Text("Scoreis") }) },
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onSetRestToZeroClick) {
                    Icon(vectorResource(id = R.drawable.ic_set_rest_to_zero))
                }
                IconButton(onClick = onAddPlayerClick) {
                    Icon(vectorResource(id = R.drawable.ic_add_player))
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                icon = { Icon(Icons.Filled.Add) },
                shape = fabShape,
                onClick = onAddScoreClick,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bodyContent = content
    )
}


@Preview
@Composable
fun AppLayoutPreview() {
    ScoreisTheme {
        val (selected, onSelected) = state { true }

        AppLayout(
            onAddScoreClick = { onSelected(!selected) }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {

                val roundedPercent = animate(if (selected) 0f else 50f).roundToInt()
                Box(
                    backgroundColor = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(roundedPercent),
                    modifier = Modifier
                        .size(150.dp)
                )
            }
        }
    }
}
