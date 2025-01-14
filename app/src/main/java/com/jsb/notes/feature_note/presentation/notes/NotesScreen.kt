package com.jsb.notes.feature_note.presentation.notes

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jsb.notes.feature_note.presentation.AppBarView
import com.jsb.notes.feature_note.presentation.notes.components.NoteItem
import com.jsb.notes.feature_note.presentation.notes.components.OrderSectionRow
import com.jsb.notes.feature_note.presentation.util.Screen
import com.jsb.notes.ui.theme.NotesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesScreen (
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is NotesViewModel.UiEvent2.NoteClicked -> {
                    navController.navigate(Screen.AddEditScreen.route
                            + "?noteId=${event.note.id}&noteColor=${event.note.color}")
                }
                is NotesViewModel.UiEvent2.AddFloatingActionButton -> {
                    navController.navigate(Screen.AddEditScreen.route)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(
            hostState = snackbarHostState,
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.background)
        ) },
        topBar = {
            AppBarView(
                title = "All notes",
                onNavigationIconClicked = {
                    // TODO: Drawer menu clicked.
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAction(NotesAction.AddFloatingActionButton) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Add new note",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        content = { paddingValue ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                OrderSectionRow(
                    modifier = modifier,
                    onClick = { viewModel.onAction(NotesAction.ToggleOrderSection) },
                    onAction = {
                        viewModel.onAction(NotesAction.Order(it))
                    },
                    visible = state.isOrderSectionVisible,
                    noteOrder = state.noteOrder,
                    onDismiss = {viewModel.onAction(NotesAction.ToggleOrderSection)}
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(if (state.notes.size == 1) 1 else 2), // 2 items per row
                ) {
                    items(state.notes) { note ->
                        NoteItem(
                            note = note,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(8.dp)
                                .clickable {
                                    viewModel.onAction(NotesAction.NoteClicked(note))
                                },
                            onDeleteClick = {
                                viewModel.onAction(NotesAction.DeleteNote(note))
                                scope.launch {
                                    val result
                                            = snackbarHostState.showSnackbar(
                                        message = "Undo delete",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed){
                                        viewModel.onAction(NotesAction.RestoreNote)
                                    }
                                    delay(10000)
                                    SnackbarResult.Dismissed
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
fun NotesScreenPreview() {
    NotesTheme {
//        NotesScreen()
    }
}