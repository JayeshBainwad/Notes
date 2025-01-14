package com.jsb.notes.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import kotlinx.coroutines.flow.SharedFlow

interface AddEditNoteContract {
    val noteTitle: State<AddEditNoteState>
    val noteContent: State<AddEditNoteState>
    val noteColor: State<Int>
    val noteFavourite: State<AddEditNoteState>
    val eventFlow: SharedFlow<AddEditNoteViewModel.UiEvent>
    fun onAction(action: AddEditNoteAction)
}
