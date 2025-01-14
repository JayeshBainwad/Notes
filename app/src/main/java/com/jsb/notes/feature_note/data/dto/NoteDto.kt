package com.jsb.notes.feature_note.data.dto

data class NoteDto(
    val title: String,
    val content: String,
    val dateTime: String,
    val color: Int,
    val timeStamp: Long,
    val isFavourite: Boolean,
    val id: Int? = null
)
