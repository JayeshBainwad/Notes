package com.jsb.notes.feature_note.presentation.notes.components.order_section

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jsb.notes.feature_note.domain.util.NoteOrder
import com.jsb.notes.feature_note.domain.util.OrderType
import com.jsb.notes.ui.theme.NotesTheme

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun OrderSectionScreen(
    modifier: Modifier = Modifier,
    onOrderChange: (NoteOrder) -> Unit,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
) {

    Box(
        modifier = modifier,
        content = {
            Column(
                modifier = modifier
                    .height(145.dp)
                    .width(185.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant), // Add padding for inner spacing
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                CustomRadioButton(
                    text = "Title",
                    selected = noteOrder is NoteOrder.Title,
                    onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
                )
                CustomRadioButton(
                    text = "Date modified",
                    selected = noteOrder is NoteOrder.Date,
                    onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
                )
                CustomRadioButton(
                    text = "Color",
                    selected = noteOrder is NoteOrder.Color,
                    onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun OrderSectionScreenPreview(){
    NotesTheme {
        OrderSectionScreen(onOrderChange = {})
    }
}