package com.jsb.notes.feature_note.domain.use_cases

import com.jsb.notes.feature_note.domain.model.InvalidNoteException
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note) {
        if (note.title.isBlank()){
            throw InvalidNoteException("Enter title to save note.")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("Enter content to same note.")
        }
        repository.insertNote(note)
    }
}