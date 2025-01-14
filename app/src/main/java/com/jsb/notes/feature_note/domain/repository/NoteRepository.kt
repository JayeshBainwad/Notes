package com.jsb.notes.feature_note.domain.repository

import com.jsb.notes.feature_note.data.data_source.NoteDao
import com.jsb.notes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun deleteNote(note: Note)
    suspend fun insertNote(note: Note)
}