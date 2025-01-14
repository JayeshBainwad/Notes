package com.jsb.notes.feature_note.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.jsb.notes.feature_note.data.dto.NotesDateDto
import java.time.Instant
import java.time.ZoneId

//@RequiresApi(Build.VERSION_CODES.O)
//fun NotesDateDto.toNotesDate(): NotesDate {
//    return NotesDate(
//        dateTime = Instant
//            .ofEpochMilli(time)
//            .atZone(ZoneId.systemDefault())
//    )
//}