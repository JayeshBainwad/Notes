package com.jsb.notes.feature_note.presentation.add_edit_note.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.jsb.notes.ui.theme.DarkGray
import com.jsb.notes.ui.theme.LightBlue
import com.jsb.notes.ui.theme.LightPurple
import com.jsb.notes.ui.theme.LightWhite
import com.jsb.notes.ui.theme.LightYellow
import com.jsb.notes.ui.theme.LightPink
import com.jsb.notes.ui.theme.LightGreen

// Function to map the ARGB value of note color to the corresponding tint color
@Composable
fun getTintColor(noteColor: Int): Color {
    return when (noteColor) {
        DarkGray.toArgb() -> MaterialTheme.colorScheme.onSurface
        LightWhite.toArgb() -> MaterialTheme.colorScheme.surfaceBright
        LightYellow.toArgb() -> MaterialTheme.colorScheme.surfaceBright
        LightBlue.toArgb() -> MaterialTheme.colorScheme.surfaceBright
        LightPurple.toArgb() -> MaterialTheme.colorScheme.surfaceBright
        LightPink.toArgb() -> MaterialTheme.colorScheme.surfaceBright
        LightGreen.toArgb() -> MaterialTheme.colorScheme.surfaceBright
        else -> MaterialTheme.colorScheme.surfaceBright // Default tint color
    }
}