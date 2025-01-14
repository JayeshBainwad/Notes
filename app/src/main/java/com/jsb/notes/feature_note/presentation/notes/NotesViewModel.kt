package com.jsb.notes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.domain.use_cases.NoteUseCases
import com.jsb.notes.feature_note.domain.util.NoteOrder
import com.jsb.notes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {
    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    private val _eventFlow = MutableSharedFlow<UiEvent2>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onAction(action: NotesAction){
        when(action) {
            is NotesAction.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is NotesAction.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(action.note)
                    recentlyDeletedNote = action.note
                }
            }
            is NotesAction.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is  NotesAction.Order -> {
                if (_state.value.noteOrder == action.noteOrder
                    && _state.value.noteOrder.orderType == action.noteOrder.orderType) {
                    return
                }
                _state.value = state.value.copy(
                    noteOrder = action.noteOrder
                )
                getNotes(action.noteOrder)
            }
            is NotesAction.NoteClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent2.NoteClicked(action.note))
                }
            }
            is  NotesAction.AddFloatingActionButton -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent2.AddFloatingActionButton)
                }
            }
        }
    }
    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        viewModelScope.launch {
            getNotesJob = noteUseCases
                .getNotes(noteOrder)
                .onEach { notes ->
                    // Update the state with the fetched notes and note order

                    _state.value = state.value.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    sealed class UiEvent2() {
        data class NoteClicked(val note: Note): UiEvent2()
        object AddFloatingActionButton: UiEvent2()
    }

}

