package com.jsb.notes.feature_note.presentation.notes

import androidx.navigation.NavController
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.domain.util.NoteOrder

sealed class NotesAction {
    data class Order(val noteOrder: NoteOrder): NotesAction()
    data class DeleteNote(val note: Note): NotesAction()
    data class NoteClicked(val note: Note): NotesAction()
    object AddFloatingActionButton: NotesAction()
    object RestoreNote: NotesAction()
    object ToggleOrderSection: NotesAction()
}