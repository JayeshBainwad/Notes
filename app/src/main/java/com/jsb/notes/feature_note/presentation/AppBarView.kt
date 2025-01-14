package com.jsb.notes.feature_note.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    title: String,
    onBackNavClicked: () -> Unit = {},
    onNavigationIconClicked: () -> Unit = {}
) {
    val navigationIcon: (@Composable () -> Unit)? = when {
        title.contains("Favourite notes") -> {
            {
                IconButton(onClick = onNavigationIconClicked) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle Drawer",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        title.contains("All notes") -> {
            {
                IconButton(onClick = onNavigationIconClicked) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle Drawer",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        else -> null
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        },
        navigationIcon = navigationIcon?:{},
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .shadow(elevation = 3.dp)
    )
}