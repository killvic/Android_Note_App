package com.example.note_app_hw.Help_Functions

import com.example.note_app_hw.Note_DB.NoteEntity
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.concurrent.ThreadLocalRandom

// HARDCODE FOR TESTING
fun hardCodeFill(): List<NoteEntity>{
    val noteList = mutableListOf<NoteEntity>()

    // Generate 20 NoteEntity objects with random dates
    repeat(20) {
        val name = "Note $it"
        val text = "This is note $it"
        val lastChange = generateRandomDate()
        val note = NoteEntity(name, text, lastChange)
        noteList.add(note)
    }
    return noteList
}

fun generateRandomDate(): Long {
    val startDate = LocalDate.of(1900, 1, 1)
    val endDate = LocalDate.of(1930, 3, 16)
    val startMillis = startDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    val endMillis = endDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    return ThreadLocalRandom.current().nextLong(startMillis, endMillis)
}