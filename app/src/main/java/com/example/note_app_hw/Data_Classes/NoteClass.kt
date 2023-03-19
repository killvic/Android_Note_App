package com.example.note_app_hw.Data_Classes

data class NoteClass(
    var name: String = "",
    var text: String = "",
    var lastChange: Long = 0,
    var id: Int = 0,
    ) : NoteListItem

fun areNotesTheSame(oldNoteObject: NoteClass, newNoteObject: NoteClass): Boolean{
    return when {
        oldNoteObject.id != newNoteObject.id -> { false }
        oldNoteObject.name != newNoteObject.name -> { false }
        oldNoteObject.text != newNoteObject.text -> { false }
        oldNoteObject.lastChange != newNoteObject.lastChange -> { false }
    else -> true
    }
}

