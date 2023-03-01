package com.example.note_app_hw

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_hw.Note_DB.NoteDAO
import com.example.note_app_hw.Note_DB.NoteEntity
import com.example.note_app_hw.Note_DB.NotesDB
import com.example.note_app_hw.note_package.NoteClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var btAddNote: FloatingActionButton
    private lateinit var btDeleteOne: FloatingActionButton
    private lateinit var btEdit: FloatingActionButton
    private val adapter: RecyclerViewAdapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) { // логика вьюх
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        //adapter = RecyclerViewAdapter()

        adapter.onClick = {
            val intent = Intent(this@MainActivity, NoteEditActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(this) // also there's grid, staggered layout

        btAddNote = findViewById(R.id.fabAddNote)
        btAddNote.setOnClickListener {
            addNewNoteScreen()
        }

        btDeleteOne = findViewById(R.id.fabDeleteOne)
        btDeleteOne.setOnClickListener {
            // add dialog window asking for id
            // delete chosen note
            TODO("Not yet implemented")
        }

        btEdit = findViewById(R.id.fabEdit)
        btEdit.setOnClickListener {
            // add dialog window asking for id
            // delete chosen note
            TODO("Not yet implemented")
        }
    }

    override fun onStart() { // запросы к дб
        super.onStart()
        val entityList = NotesDB.getDatabase(this).noteDao().readAllNotes()
        adapter.notes = entityToClassConverter(entityList)
        adapter.notifyDataSetChanged()

        // DONE - сделать запрос getAll, получаю лист и кладу в адаптер
        // DONE - законвертить этот лист в нотекласс (через map)
        // DONE - и отправить готовый список в адаптер
        //  - не забыть про обновление дб после каждого изменения
        //  - возможно нужно использовать DiffUtil
    }


    // Converts list of NoteEntities to List of NoteClass in order to pass it to RecyclerViewAdapter
    private fun entityToClassConverter(entityList: List<NoteEntity>): List<NoteClass> {
        var noteClassList: List<NoteClass> = entityList.map { entity ->
            NoteClass(
                id = entity.id ?: 0,
                lastChange = entity.lastChange,
                name = entity.name,
                text = entity.text
            )
        }
        return noteClassList
    }

    private fun deleteNote() {
        NotesDB.getDatabase(this).noteDao().delete(NoteEntity(id = 1))
        TODO("Not yet implemented")
    }

    private fun editNote() {
        TODO("Not yet implemented")
    }

    fun addNewNoteScreen() {
        val intent = Intent(this, NoteEditActivity::class.java)
        startActivityForResult(intent, 1)
    }
}