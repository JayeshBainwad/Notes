package com.jsb.notes.feature_note.presentation.notes.components.order_section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jsb.notes.feature_note.domain.util.NoteOrder
import com.jsb.notes.feature_note.domain.util.OrderType

@Composable
fun OrderTypeIconChange(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onClick: (NoteOrder) -> Unit
) {
    when(noteOrder) {
        is NoteOrder.Date -> {
            when(noteOrder.orderType) {
                is OrderType.Descending -> {
                    DefaultIconButton(
                        modifier = modifier,
                        onClick = {
                            onClick(it)
                        },
                        noteOrder = noteOrder
                    )
                }
                is OrderType.Ascending -> {
                    DefaultIconButton(
                        modifier = modifier,
                        onClick = {
                            onClick(it)
                        },
                        noteOrder = noteOrder
                    )
                }
            }
        }
        is NoteOrder.Title -> {
            when(noteOrder.orderType) {
                is OrderType.Descending -> {
                    DefaultIconButton(
                        modifier = modifier,
                        onClick = { onClick(NoteOrder.Title(OrderType.Ascending)) },
                        noteOrder = noteOrder
                    )
                }
                is OrderType.Ascending -> {
                    DefaultIconButton(
                        modifier = modifier,
                        onClick = { onClick(NoteOrder.Title(OrderType.Descending)) },
                        noteOrder = noteOrder
                    )
                }
            }
        }
        is NoteOrder.Color -> {
            when(noteOrder.orderType) {
                is OrderType.Descending -> {
                    DefaultIconButton(
                        modifier = modifier,
                        onClick = { onClick(NoteOrder.Color(OrderType.Ascending)) },
                        noteOrder = noteOrder
                    )
                }
                is OrderType.Ascending -> {
                    DefaultIconButton(
                        modifier = modifier,
                        onClick = { onClick(NoteOrder.Color(OrderType.Descending)) },
                        noteOrder = noteOrder
                    )
                }
            }
        }
    }
}