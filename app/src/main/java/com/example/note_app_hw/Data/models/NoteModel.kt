package com.example.note_app_hw.Data.models

class NoteModel(
    var name: String = "",
    var text: String = "",
    var lastChange: Long = 0,
    var id: Int = 0,
    var favorite: Boolean = false
)