package com.jsb.notes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jsb.notes.ui.theme.DarkGray
import com.jsb.notes.ui.theme.LightBlue
import com.jsb.notes.ui.theme.LightGreen
import com.jsb.notes.ui.theme.LightPink
import com.jsb.notes.ui.theme.LightPurple
import com.jsb.notes.ui.theme.LightWhite
import com.jsb.notes.ui.theme.LightYellow

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val timeStamp: Long,
    val isFavourite: Boolean,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColor = listOf(DarkGray, LightWhite, LightYellow, LightBlue, LightPurple, LightPink, LightGreen)
    }
}
class InvalidNoteException(message: String): Exception()