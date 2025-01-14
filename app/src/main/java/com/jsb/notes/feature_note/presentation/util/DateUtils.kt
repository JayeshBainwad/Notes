package com.jsb.notes.feature_note.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun formatNoteDateTime(noteTimeStamp: Long): String {
    val noteTime = Instant.ofEpochMilli(noteTimeStamp).atZone(ZoneId.systemDefault())
    val currentTime = Instant.now().atZone(ZoneId.systemDefault())
    val hoursDifference = ChronoUnit.HOURS.between(noteTime, currentTime)

    return if (hoursDifference <= 12) {
        DateTimeFormatter.ofPattern("h:mm a").format(noteTime) // e.g., "3:45 PM"
    } else {
        DateTimeFormatter.ofPattern("d MMM y").format(noteTime) // e.g., "7 Jan 2025"
    }
}