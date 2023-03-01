package com.example.note_app_hw

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.note_app_hw.Note_DB.NoteDAO
import com.example.note_app_hw.Note_DB.NoteEntity
import com.example.note_app_hw.Note_DB.NotesDB
import java.text.SimpleDateFormat
import java.util.*

class NoteEditActivity : AppCompatActivity() {
    private lateinit var btSaveNote: Button
    private lateinit var etNoteName: EditText
    private lateinit var etNoteText: EditText
    private lateinit var btDeleteNote: Button
    var editMode: Boolean = false // false - Create mode, true - Edit Mode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        // views initialization
        btSaveNote = findViewById(R.id.btSaveNote)
        etNoteName = findViewById(R.id.etNoteName)
        etNoteText = findViewById(R.id.etNoteText)
        btDeleteNote = findViewById(R.id.btDeleteNote)
        var id = intent.getIntExtra("id", -1)
        editMode = mode(id)

        if (editMode) {
            etNoteName.setText(NotesDB.getDatabase(this).noteDao().loadSingle(id).name)
            etNoteText.setText(NotesDB.getDatabase(this).noteDao().loadSingle(id).text)

            btDeleteNote.setOnClickListener{
                deleteNoteFromDatabase(id)
                onBackPressed()
            }
            btSaveNote.setOnClickListener{
                updateNoteInDatabase(id)
                onBackPressed()
            }
        }
        else {
            btSaveNote.setOnClickListener{
                insertNoteToDatabase()
                onBackPressed()
            }
        }
    }

    // <------------------------------>
    // Help Functions

    // This function returns a mode in which this activity is launched
    // Where false is for a Create mode, and true is for an Edit mode
    private fun mode(id: Int): Boolean {
        if (id >= 0)
            return true
        return false
    }

    // <------------------------------------>
    // Functions for new Note Initialization

    // Converts Long to Time
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    // Takes information from edit textes and creates a NoteEntity
    private fun createNoteEntity(): NoteEntity {
        return NoteEntity(
            etNoteName.text.toString(),
            etNoteText.text.toString(),
            convertLongToTime(System.currentTimeMillis())
        )
    }

    // updates db
    private fun updateNoteEntity(id: Int): NoteEntity {
        var noteForEditing = NotesDB.getDatabase(this).noteDao().loadSingle(id)
        noteForEditing.name = etNoteName.text.toString()
        noteForEditing.text = etNoteText.text.toString()
        return noteForEditing
    }

    // Functions for new Note Initialization
    // <------------------------------------>
    // Room Database Functions

    // Calls for CreateNoteEntity() and adds returned entit to database
    private fun insertNoteToDatabase() {
        NotesDB.getDatabase(this).noteDao().insert(createNoteEntity())
    }

    private fun updateNoteInDatabase(id: Int) {
        NotesDB.getDatabase(this).noteDao().update(updateNoteEntity(id))
    }

    private fun deleteNoteFromDatabase(id: Int) {
        NotesDB.getDatabase(this).noteDao().deleteById(id)
    }
}
    // Room Database Functions
    // <----------------------------------->


