package com.jsb.notes.feature_note.model

data class NoteUi(
    val title: String,
    val content: String,
    val color: Int,
    val timeStamp: Long,
    val isFavourite: Boolean,
    val id: Int? = null
)


