package com.jsb.notes.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.Color
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.domain.util.NoteOrder

data class AddEditNoteState(
    val text: String = "",
    val hint: String = "",
    val dateTime: String = "",
    val isHintVisible: Boolean = true,
    val isFavourite: Boolean = false
)
