package com.example.note_app_hw.ObjectClasses

import android.provider.ContactsContract.CommonDataKinds.Note

data class NoteClass(
    var name: String = "",
    var text: String = "",
    var lastChange: Long = 0,
    override var id: Int = 0,
    var favorite: Boolean = false
    ) : NoteListItem

fun areNotesTheSame(oldNoteObject: NoteClass, newNoteObject: NoteClass): Boolean{
    return when {
        oldNoteObject.id != newNoteObject.id -> { false }
        oldNoteObject.name != newNoteObject.name -> { false }
        oldNoteObject.text != newNoteObject.text -> { false }
        oldNoteObject.lastChange != newNoteObject.lastChange -> { false }
        oldNoteObject.favorite != newNoteObject.favorite -> { false }
    else -> true
    }
}

