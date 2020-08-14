package com.example.compse_ui.ui

import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.size
import androidx.ui.material.*
import androidx.ui.material.MaterialTheme.colors
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Menu
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp


@Composable
fun AppLayout(content: @Composable () -> Unit) {
    val scaffoldState = remember { ScaffoldState() }
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { Text("Drawer content") },
        topAppBar = {
            TopAppBar(
                title = { Text("Simple Scaffold Screen") },
                navigationIcon = {
                    IconButton(onClick = {
                        scaffoldState.drawerState = DrawerState.Opened
                    }) {
                        Icon(Icons.Filled.Menu)
                    }
                }
            )
        },
        floatingActionButtonPosition = Scaffold.FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Inc") },
                onClick = { /* fab click handler */ }
            )
        },
        bodyContent = { modifier ->
            Surface(modifier = modifier) {
               content()
            }
        }
    )
}


@Preview
@Composable
fun AppLayoutPreview() {
    ScoreisTheme {
        AppLayout {
            Box(
                modifier = Modifier.size(25.dp),
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                shape = CircleShape
            )
        }
    }
}