package com.jsb.notes.feature_note.presentation.notes.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.jsb.notes.feature_note.domain.util.NoteOrder
import com.jsb.notes.feature_note.domain.util.OrderType
import com.jsb.notes.feature_note.presentation.notes.components.order_section.OrderSectionScreen
import com.jsb.notes.feature_note.presentation.notes.components.order_section.OrderTypeIconChange
import com.jsb.notes.ui.theme.NotesTheme
import kotlinx.coroutines.delay

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun OrderSectionRow(
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onClick: () -> Unit,
    onAction: (NoteOrder) -> Unit,
    onDismiss: () -> Unit // Callback to dismiss when touching outside
) {
    Row(
        modifier = modifier
            .height(30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = "Sort notes",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "", color = MaterialTheme.colorScheme.primary)
        }
        Text(
            text = "|",
            modifier = Modifier.padding(horizontal = 4.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 22.sp
        )
        OrderTypeIconChange(
            modifier = modifier,
            noteOrder = noteOrder,
            onClick = {
                onAction(it)
            }
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(70)) +
                scaleIn(animationSpec = tween(100, easing = FastOutSlowInEasing)),
        exit = fadeOut(animationSpec = tween(70)) +
                scaleOut(animationSpec = tween(100, easing = FastOutSlowInEasing))
    ) {
        Popup(
            offset = IntOffset(
                x = 560,
                y = -60
            ),
            onDismissRequest = onDismiss,
            properties = PopupProperties(
                focusable = true, // Ensures it dismisses on outside click
                dismissOnClickOutside = true // Additional safety for outside clicks
            )
        ) {
            Box(
                modifier = Modifier
                    .height(145.dp)
                    .width(180.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Transparent)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                , // Transparent background for clipped content
                contentAlignment = Alignment.Center
            ) {
                OrderSectionScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp)), // Ensure the screen is clipped
                    noteOrder = noteOrder,
                    onOrderChange = {
                        onAction(it)
                        onDismiss()
                    }
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
fun OrderSectionRowPreview() {
    NotesTheme {
        var visible by remember { mutableStateOf(false) }
        OrderSectionRow(
            visible = visible,
            onClick = { visible = !visible },
            onDismiss = { visible = false }, // Hide when touching outside
            onAction = { /* Handle order change */ }
        )
    }
}
