package com.example.note_app_hw.note_package

data class NoteClass(
    var name: String = "",
    var text: String = "",
    var lastChange: String = "",
    var id: Int = 0
    )

fun areNotesTheSame(oldNoteObject: NoteClass, newNoteObject: NoteClass): Boolean{
    return when {
        oldNoteObject.id != newNoteObject.id -> { false }
        oldNoteObject.name != newNoteObject.name -> { false }
        oldNoteObject.text != newNoteObject.text -> { false }
        oldNoteObject.lastChange != newNoteObject.lastChange -> { false }
    else -> true
    }
}

