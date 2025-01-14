package com.jsb.notes.feature_note.presentation.add_edit_note

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.presentation.add_edit_note.components.AddEditTextField
import com.jsb.notes.feature_note.presentation.add_edit_note.util.getTintColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val animatableNoteColor = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    val favouriteState = viewModel.noteFavourite.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {event ->
            when(event) {
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp, // No elevation by default
                        pressedElevation = 0.dp, // No elevation when pressed
                        focusedElevation = 5.dp, // No elevation when focused
                        hoveredElevation = 0.dp, // No elevation when hovered
                    ), // Disable shadow
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = { viewModel.onAction(AddEditNoteAction.IsFavouriteNote) },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Save note",
                            tint = if (favouriteState.isFavourite) Color.Yellow else getTintColor(
                                noteColor = noteColor
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    onClick = { viewModel.onAction(AddEditNoteAction.SaveNote) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save note"
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .background(color = animatableNoteColor.value)
                    .padding(16.dp)
                    .padding(it)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Note.noteColor.forEach { color ->
                        val colorInt = color.toArgb()
                        Box(
                            modifier = Modifier
                                .size(45.dp) // Add size for consistent box dimensions
                                .clip(RectangleShape)
                                .background(color = color)
                                .border(
                                    width = if (colorInt == viewModel.noteColor.value) 1.5.dp else 0.dp,
                                    color = if (colorInt == viewModel.noteColor.value) Color.Black else Color.Transparent,
                                    shape = RectangleShape
                                )
                                .clickable {
                                    scope.launch {
                                        animatableNoteColor.animateTo(
                                            targetValue = Color(colorInt),
                                            animationSpec = tween(
                                                durationMillis = 500
                                            )
                                        )
                                    }
                                    viewModel.onAction(AddEditNoteAction.NoteColorChanged(colorInt))
                                }
                        )
                    }
                }
                AddEditTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 22.sp
                    ),
                    onValueChange = {title ->
                        viewModel.onAction(AddEditNoteAction.TitleEntered(title))
                    },
                    isHintVisible = titleState.isHintVisible
                )
                Spacer(modifier = Modifier.height(12.dp))
                AddEditTextField(
                    modifier = Modifier
                        .fillMaxHeight(),
                    text = contentState.text,
                    hint = contentState.hint,
                    singleLine = false,
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    onValueChange = {content ->
                        viewModel.onAction(AddEditNoteAction.ContentEntered(content))
                    },
                    isHintVisible = contentState.isHintVisible
                )
            }
        }
    )
}