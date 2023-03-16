package com.example.note_app_hw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_hw.Note_DB.NoteEntity
import com.example.note_app_hw.Note_DB.NotesDB
import com.example.note_app_hw.ObjectClasses.DateClass
import com.example.note_app_hw.ObjectClasses.NoteClass
import com.example.note_app_hw.ObjectClasses.NoteListItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.concurrent.ThreadLocalRandom

// TO-DO
//

// EXTRA FEATURES:
// - Every new note will have different background color
// - Add feature, that will allow user to download pictures to notes
class MainActivity : AppCompatActivity() {
    private lateinit var btAddNote: FloatingActionButton
    private lateinit var btFavoriteFolder: FloatingActionButton
    private val adapter = RecyclerViewAdapterMultipleItems()

    override fun onCreate(savedInstanceState: Bundle?) { // views logic
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        adapter.onClick = {
            val intent = Intent(this@MainActivity, NoteEditActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        recyclerView.layoutManager =
            LinearLayoutManager(this) // also there's grid, staggered layout

        btAddNote = findViewById(R.id.fabAddNote)
        btAddNote.setOnClickListener {
            addNewNoteScreen()
        }

        btFavoriteFolder = findViewById(R.id.favFavoriteFolder)
        btFavoriteFolder.setOnClickListener{
            favoriteFolderScreen()
        }
    }

    override fun onStart() { // database logic
        super.onStart()
        NotesDB.getDatabase(this).noteDao().insertAll(hardCodeFill())
        val entityList = NotesDB.getDatabase(this).noteDao().readAllNotes()
        val dateList = entityList.map{it.lastChange}
        adapter.setData(entityToClassConverter(entityList))

    }

    // Converts list of NoteEntities to List of NoteClass in order to pass it to RecyclerViewAdapter
    private fun entityToClassConverter(entityList: List<NoteEntity>): List<NoteListItem> {
        var noteClassList: List<NoteClass> = entityList.map { entity ->
            NoteClass(
                id = entity.id ?: 0,
                lastChange = entity.lastChange,
                name = entity.name,
                text = entity.text,
                favorite = entity.favorite
            )
        }
        var dateList: List<DateClass> = entityList.map { entity ->
            DateClass(
                date = truncateTimeFromMillis(entity.lastChange)
            )
        }
            .distinctBy { it.date }
            .sortedByDescending { it.date }

        return combineList(noteClassList, dateList)
    }

    fun combineList(noteList: List<NoteClass>, dateList: List<DateClass>) : List<NoteListItem> {
        val combinedList = mutableListOf<NoteListItem>()
        for (date in dateList) {
            combinedList.add(date)
            for (note in noteList) {
                if (date.date == truncateTimeFromMillis(note.lastChange))
                    combinedList.add(note)
            }
        }
        return combinedList
    }

    fun addNewNoteScreen() {
        val intent = Intent(this, NoteEditActivity::class.java)
        startActivity(intent)
    }

    fun favoriteFolderScreen() {
        val intent = Intent(this, FavoriteFolderActivity::class.java)
        startActivity(intent)
    }

    fun truncateTimeFromMillis(currentTimeInMillis: Long): Long {
        // Get the number of milliseconds since midnight (i.e., the time portion)
        val timeInMillis = currentTimeInMillis % (24 * 60 * 60 * 1000)

        // Subtract the time portion from the current time to get the date
        val dateInMillis = currentTimeInMillis - timeInMillis

        // Return the date as a Long
        return dateInMillis
    }

    // HARDCODE FOR TESTING
    fun hardCodeFill(): List<NoteEntity>{
        val noteList = mutableListOf<NoteEntity>()

        // Generate 20 NoteEntity objects with random dates
        repeat(20) {
            val name = "Note $it"
            val text = "This is note $it"
            val lastChange = generateRandomDate()
            val favorite = it % 2 == 0
            val note = NoteEntity(name, text, lastChange, favorite)
            noteList.add(note)
        }
        Log.d("ggg", noteList.toString())
        return noteList
    }

    fun generateRandomDate(): Long {
        val startDate = LocalDate.of(2020, 1, 1)
        val endDate = LocalDate.of(2023, 3, 16)
        val startMillis = startDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        val endMillis = endDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        return ThreadLocalRandom.current().nextLong(startMillis, endMillis)
    }
}