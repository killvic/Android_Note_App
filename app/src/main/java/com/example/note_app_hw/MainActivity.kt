package com.example.note_app_hw

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        //adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) // also there's grid, staggered layout

        btAddNote = findViewById(R.id.fabAddNote)
        btAddNote.setOnClickListener{
            addNewNoteScreen()
        }

        btDeleteOne = findViewById(R.id.fabDeleteOne)
        btDeleteOne.setOnClickListener{
            // add dialog window asking for id
            // delete chosen note
            TODO("Not yet implemented")
        }

        btEdit = findViewById(R.id.fabEdit)
        btEdit.setOnClickListener{
            // add dialog window asking for id
            // delete chosen note
            TODO("Not yet implemented")
        }
    }

    private fun deleteNote() {
        NotesDB.getDatabase(this).noteDao().delete(NoteEntity(id = 1))
        TODO("Not yet implemented")
    }

    private fun editNote() {
        TODO("Not yet implemented")
    }

    private fun chooseIdDialog() {

    }

    fun addNewNoteScreen(){
        val intent = Intent(this, NoteEditActivity::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val result = data!!.getStringExtra("result")
                adapter.notes += listOf(data.getSerializableExtra("result") as NoteClass)
                // adapter.notes = listOf(data.getParcelableExtra<NoteClass>("result") as NoteClass)
                adapter.notifyDataSetChanged()
            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}

