package com.jsb.notes.feature_note.presentation.drawer

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id: String = "",
    val title: String = "",
    val icon: ImageVector? = null,
    val description: String = "",
    val isSelected: Boolean = false
)
