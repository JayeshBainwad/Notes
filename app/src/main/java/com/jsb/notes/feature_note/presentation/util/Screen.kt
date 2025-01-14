package com.jsb.notes.feature_note.presentation.util

sealed class Screen(val route: String) {
    object NotesScreen: Screen("Notes_Screen")
    object AddEditScreen: Screen("Add_Edit_Screen")
}