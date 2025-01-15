package com.jsb.notes.feature_note.presentation.add_edit_note

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsb.notes.feature_note.domain.model.InvalidNoteException
import com.jsb.notes.feature_note.domain.model.InvalidNotesDate
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _noteTitle = mutableStateOf(AddEditNoteState(
        hint = "Title"
    ))
    val noteTitle: State<AddEditNoteState> = _noteTitle

    private val _noteContent = mutableStateOf(AddEditNoteState(
        hint = "Content"
    ))
    val noteContent: State<AddEditNoteState> = _noteContent

    private val _noteDateTime = mutableStateOf(AddEditNoteState())
    val noteDateTime: State<AddEditNoteState> = _noteDateTime

    val _noteFavourite = mutableStateOf(AddEditNoteState())
    val noteFavourite: State<AddEditNoteState> = _noteFavourite

    private val _noteColor = mutableStateOf(Note.noteColor.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private var currentNoteId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let {noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also {note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = note.title.isBlank()
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isFavourite = note.isFavourite,
                            isHintVisible = note.content.isBlank()
                        )
                        _noteColor.value = note.color
                        _noteFavourite.value = noteFavourite.value.copy(
                            isFavourite = note.isFavourite
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAction(action: AddEditNoteAction) {
        when (action) {
            is AddEditNoteAction.TitleEntered -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = action.title,
                    isHintVisible = action.title.isBlank()
                )
            }
            is AddEditNoteAction.ContentEntered -> {
                _noteContent.value = noteContent.value.copy(
                    text = action.content,
                    isHintVisible = action.content.isBlank()
                )
            }
            is AddEditNoteAction.NoteColorChanged -> {
                _noteColor.value = action.color
            }
            is AddEditNoteAction.IsFavouriteNote -> {
                _noteFavourite.value = noteFavourite.value.copy(
                    isFavourite = !noteFavourite.value.isFavourite
                )
            }
            is AddEditNoteAction.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = _noteTitle.value.text,
                                content = _noteContent.value.text,
                                color = _noteColor.value,
                                timeStamp = System.currentTimeMillis(),
                                isFavourite = _noteFavourite.value.isFavourite,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                            message = e.message?: "Unknown error, couldn't save note"
                        ))
                    }
                }
            }
        }
    }
    sealed class UiEvent() {
        object SaveNote: UiEvent()
        data class ShowSnackBar(val message: String): UiEvent()
    }
}