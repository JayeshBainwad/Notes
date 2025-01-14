package com.jsb.notes.feature_note.presentation.notes.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.jsb.notes.feature_note.domain.model.Note
import com.jsb.notes.feature_note.presentation.util.formatNoteDateTime
import com.jsb.notes.ui.theme.NotesTheme
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    cutCornerSize: Dp = 30.dp,
    cornerRadius: Dp = 10.dp,
    note: Note
){
    val textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)


    Box(modifier = modifier) {

        // Convert the Dp to Px using LocalDensity inside the Composable context
        val cutCornerPx = with(LocalDensity.current) { cutCornerSize.toPx() }
        val cornerRadiusPx = with(LocalDensity.current) { cornerRadius.toPx() }

        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerPx, 0f)
                lineTo(size.width, cutCornerPx)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath){
                drawRoundRect(
                    size = size,
                    color = Color(note.color),
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )

                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, 0x000000, 0.3f)
                    ),
                    topLeft = Offset(
                        x = size.width - cutCornerPx,
                        y = -100f
                    ),
                    size = Size(
                        width = cutCornerPx + 100f,
                        height = cutCornerPx + 100f
                    ),
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(start = 6.dp, top = 6.dp)
                .clip(
                    RoundedCornerShape(
                        topEnd = 36.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //        TODO: Add preview image of note
            Column {
                Text(
                    text = note.title,
                    style = textStyle.copy(
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.scrim,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = textStyle.copy(
                        fontSize = 12.sp
                    ),
                    color = MaterialTheme.colorScheme.scrim,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 6.dp),
                        text = formatNoteDateTime(note.timeStamp),
                        style = textStyle.copy(
                            fontSize = 12.sp
                        ),
                        color = MaterialTheme.colorScheme.scrim,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    if (note.isFavourite){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favourite note",
                            tint = Color.Yellow,
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = onDeleteClick,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete note",
                                tint = MaterialTheme.colorScheme.scrim,
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .size(16.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
fun NoteItemPreview() {
    NotesTheme {
        NoteItem(onDeleteClick = { /*TODO*/ }, note = previewNote)
    }
}

internal val timeStamp: Long = System.currentTimeMillis()

@RequiresApi(Build.VERSION_CODES.O)
internal val time: ZonedDateTime = Instant
    .ofEpochMilli(timeStamp)
    .atZone(ZoneId.systemDefault())

@RequiresApi(Build.VERSION_CODES.O)
internal val previewNote = Note(
    title = "Title",
    content = "Content",
    timeStamp = timeStamp,
    color = Color.Blue.toArgb(),
    isFavourite = true,
    id = 1
)