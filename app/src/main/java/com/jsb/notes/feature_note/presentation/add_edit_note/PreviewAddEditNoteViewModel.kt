package com.jsb.notes.feature_note.presentation.add_edit_note

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import com.jsb.notes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@SuppressLint("UnrememberedMutableState")
@Composable
fun PreviewAddEditNoteViewModel(): AddEditNoteContract {
    return object : AddEditNoteContract {

        override val noteTitle = mutableStateOf(AddEditNoteState(text = "ABC", hint = "Title"))
        override val noteContent = mutableStateOf(AddEditNoteState(text = "Sample Content", isHintVisible = false))
        override val noteColor = mutableStateOf(Note.noteColor.last().toArgb())
        override val noteFavourite = mutableStateOf(AddEditNoteState(isFavourite = true))
        override val eventFlow = MutableSharedFlow<AddEditNoteViewModel.UiEvent>().asSharedFlow()
        override fun onAction(action: AddEditNoteAction) {
            // No-op for preview.
        }
    }
}
