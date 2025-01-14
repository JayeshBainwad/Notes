package com.jsb.notes.feature_note.presentation.notes

import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.domain.util.NoteOrder
import com.jsb.notes.feature_note.domain.util.OrderType

data class NotesState(
    val isOrderSectionVisible: Boolean = false,
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false
)
