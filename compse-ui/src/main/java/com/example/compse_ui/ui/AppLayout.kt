package com.example.compse_ui.ui

import androidx.compose.Composable
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.animation.animate
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.compse_ui.R
import kotlin.math.roundToInt


@Composable
fun AppLayout(
    onSetRestToZeroClick: () -> Unit = {},
    onAddPlayerClick: () -> Unit = {},
    onAddScoreClick: () -> Unit = {},
    content: @Composable (Modifier) -> Unit
) {
    val scaffoldState = remember { ScaffoldState() }
    val fabShape = CircleShape

    Scaffold(
        scaffoldState = scaffoldState,
        topAppBar = { TopAppBar(title = { Text("Scoreis") }) },
        bottomAppBar = {
            BottomAppBar(
                cutoutShape = CircleShape,
                fabConfiguration = it,
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
        floatingActionButtonPosition = Scaffold.FabPosition.CenterDocked,
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
