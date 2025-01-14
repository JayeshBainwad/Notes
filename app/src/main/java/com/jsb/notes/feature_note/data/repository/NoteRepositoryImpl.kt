package com.jsb.notes.feature_note.data.repository

import com.jsb.notes.feature_note.data.data_source.NoteDao
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }
}