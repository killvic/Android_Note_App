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
                date = entity.lastChange,
                id = entity.id ?: 0
            )
        }
        var noteAndDateCombinedList: List<NoteListItem> = noteClassList + dateList
        return noteAndDateCombinedList
    }
    fun addNewNoteScreen() {
        val intent = Intent(this, NoteEditActivity::class.java)
        startActivity(intent) //
    }

    fun favoriteFolderScreen() {
        val intent = Intent(this, FavoriteFolderActivity::class.java)
        startActivity(intent)
    }
}