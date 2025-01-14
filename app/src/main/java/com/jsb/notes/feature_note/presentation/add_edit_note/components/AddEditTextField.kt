package com.jsb.notes.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun AddEditTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    singleLine: Boolean = false,
    isHintVisible: Boolean = true,
    textStyle: TextStyle,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
                            },
            textStyle = textStyle,
            modifier = modifier
                .fillMaxWidth(),
            singleLine = singleLine
        )
        if (isHintVisible){
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}