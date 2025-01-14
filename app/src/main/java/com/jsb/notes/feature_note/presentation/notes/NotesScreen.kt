package com.jsb.notes.feature_note.presentation.notes

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jsb.notes.feature_note.presentation.AppBarView
import com.jsb.notes.feature_note.presentation.drawer.DrawerScreen
import com.jsb.notes.feature_note.presentation.drawer.MenuItem
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var displayFavoriteNotes by remember { mutableStateOf(false) }
    val itemsState = remember {
        mutableStateListOf(
            MenuItem(
                title = "All notes",
                id = "all notes",
                description = "See all notes",
                icon = Icons.Default.MenuBook,
                isSelected = true // Default selection
            ),
            MenuItem(
                title = "Favourite notes",
                id = "favourite notes",
                description = "See favourite notes",
                icon = Icons.Default.Star
            )
        )
    }
    // Calculate offset based on drawer's state
    val drawerOffsetDp = with(LocalDensity.current) { drawerState.offset.value.toDp() + 300.dp }
    val animatedOffset by animateDpAsState(targetValue = drawerOffsetDp, label = "")
    val density = LocalDensity.current
    val animatedOffsetPx = with(density) { animatedOffset.toPx() }

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

    ModalNavigationDrawer(
        modifier = modifier,
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .padding(top = 31.dp)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                Box(modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surfaceDim)
                    .height(40.dp)
                    .width(300.dp)
                )
                DrawerScreen(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surfaceDim),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    items = itemsState,
                    onClick = { menuItem ->

                        // Update selected menu state
                        itemsState.forEachIndexed { index, item ->
                            itemsState[index] = item.copy(
                                isSelected = item.id == menuItem.id
                            )
                        }

                        when (menuItem.title) {
                            "All notes" -> {
                                scope.launch { delay(150) }
                                displayFavoriteNotes = false
                                // navController.navigate(Screen.NotesScreen.route)
                            }

                            "Favourite notes" -> {
                                scope.launch { delay(150) }
                                displayFavoriteNotes = true
                                // navController.navigate(Screen.NotesScreen.route)
                            }
                        }

                        scope.launch {
                            delay(200)
                            drawerState.close()
                        }
                    }
                )
            }
        },
        content = {
            Scaffold(
                modifier = Modifier.graphicsLayer(translationX = animatedOffsetPx),
                snackbarHost = { SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = modifier
                        .background(color = MaterialTheme.colorScheme.background)
                ) },
                topBar = {
                    AppBarView(
                        title = when (displayFavoriteNotes) {
                            false -> "All notes" // Display only favorite notes
                            true -> "Favourite notes"  // Display all notes
                        },
                        onNavigationIconClicked = {
                            // TODO: Drawer menu clicked.
                            scope.launch { drawerState.open() }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { viewModel.onAction(NotesAction.AddFloatingActionButton) },
                        shape = CircleShape,
                        containerColor = MaterialTheme.colorScheme.surfaceBright,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Add new note"
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
                            items(state.notes.filter {
                                when (displayFavoriteNotes) {
                                    true -> it.isFavourite // Display only favorite notes
                                    false -> true          // Display all notes
                                }
                            }) { note ->
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
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            )
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