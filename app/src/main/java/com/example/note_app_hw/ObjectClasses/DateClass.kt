package com.example.note_app_hw.ObjectClasses

data class DateClass(
    var date: Long = 0,
) : NoteListItem

fun areDatesTheSame(oldDateObject: DateClass, newDateObject: DateClass): Boolean{
    return when {
        oldDateObject.date != newDateObject.date -> { false }
        else -> true
    }
}