package com.jsb.notes.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jsb.notes.feature_note.data.data_source.NoteDatabase
import com.jsb.notes.feature_note.data.repository.NoteRepositoryImpl
import com.jsb.notes.feature_note.domain.repository.NoteRepository
import com.jsb.notes.feature_note.domain.use_cases.AddNote
import com.jsb.notes.feature_note.domain.use_cases.DeleteNote
import com.jsb.notes.feature_note.domain.use_cases.GetNote
import com.jsb.notes.feature_note.domain.use_cases.GetNotes
import com.jsb.notes.feature_note.domain.use_cases.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDataBase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            addNote = AddNote(noteRepository),
            getNote = GetNote(noteRepository),
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository)
        )
    }
}