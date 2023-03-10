package com.example.note_app_hw.Note_DB
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
data class NoteEntity(
    var name: String = "",
    var text: String = "",
    var lastChange: Long = 0,
    var favorite: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)