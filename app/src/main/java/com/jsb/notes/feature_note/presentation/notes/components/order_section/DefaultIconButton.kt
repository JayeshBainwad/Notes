package com.jsb.notes.feature_note.presentation.notes.components.order_section

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsb.notes.feature_note.domain.util.NoteOrder
import com.jsb.notes.feature_note.domain.util.OrderType

@Composable
fun DefaultIconButton(
    modifier: Modifier = Modifier,
    onClick: (NoteOrder) -> Unit,
    noteOrder: NoteOrder
) {
    if (noteOrder.orderType == OrderType.Descending){
        IconButton(
            onClick = { onClick(noteOrder.copy(OrderType.Ascending)) }
        ) {
            Icon(
                modifier = Modifier
                    .size(21.dp),
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = "Change OrderType",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    } else {
        IconButton(
            onClick = { onClick(noteOrder.copy(OrderType.Descending)) }
        ) {
            Icon(
                modifier = Modifier
                    .size(21.dp),
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = "Change OrderType",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}