package com.example.note_app_hw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_hw.Note_DB.NoteEntity
import com.example.note_app_hw.Note_DB.NotesDB
import com.example.note_app_hw.Data_Classes.DateClass
import com.example.note_app_hw.Data_Classes.NoteClass
import com.example.note_app_hw.Data_Classes.NoteListItem
import com.example.note_app_hw.Help_Functions.hardCodeFill
import com.example.note_app_hw.Help_Functions.truncateTimeFromMillis
import com.example.note_app_hw.Recycler_View_Classes.RecyclerViewAdapterMultipleItems
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.concurrent.ThreadLocalRandom

// EXTRA FEATURES:
// - Every new note will have different background color
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
    }

    override fun onStart() { // database logic
        super.onStart()
        NotesDB.getDatabase(this).noteDao().insertAll(hardCodeFill())
        val entityList = NotesDB.getDatabase(this).noteDao().readAllNotes()
        adapter.setData(entityToClassConverter(entityList))

    }

    // Converts list of NoteEntities to List of NoteClass in order to pass it to RecyclerViewAdapter
    private fun entityToClassConverter(entityList: List<NoteEntity>): List<NoteListItem> {
        var noteClassList: List<NoteClass> = entityList.map { entity ->
            NoteClass(
                id = entity.id ?: 0,
                lastChange = entity.lastChange,
                name = entity.name,
                text = entity.text
            )
        }
        return groupList(noteClassList)
    }

    fun groupList(noteList: List<NoteClass>) : List<NoteListItem> {
        val groupedList = noteList.groupBy { truncateTimeFromMillis(it.lastChange) }
        val finalList = mutableListOf<NoteListItem>()
        groupedList.forEach{ entry ->
            finalList.add( DateClass(entry.key))
            entry.value.forEach{
                finalList.add(it)
            }
        }
        return finalList
    }

    fun addNewNoteScreen() {
        val intent = Intent(this, NoteEditActivity::class.java)
        startActivity(intent)
    }
}