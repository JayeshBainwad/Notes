package com.jsb.notes.feature_note.presentation.add_edit_note

sealed class AddEditNoteAction {
    data class TitleEntered(val title: String): AddEditNoteAction()
    data class ContentEntered(val content: String): AddEditNoteAction()
    data class NoteColorChanged(val color: Int): AddEditNoteAction()
    object IsFavouriteNote: AddEditNoteAction()
    object SaveNote: AddEditNoteAction()
}