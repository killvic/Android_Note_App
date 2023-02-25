package com.example.note_app_hw

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.note_app_hw.Note_DB.NoteDAO
import com.example.note_app_hw.Note_DB.NoteEntity
import com.example.note_app_hw.Note_DB.NotesDB
import com.example.note_app_hw.note_package.NoteClass
import java.text.SimpleDateFormat
import java.util.*

class NoteEditActivity : AppCompatActivity() {
    private lateinit var btExitNote: Button
    private lateinit var btSaveNote: Button
    private lateinit var etNoteName: EditText
    private lateinit var etNoteText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        // views initialization
        btExitNote = findViewById(R.id.btBackToNotesList)
        btSaveNote = findViewById(R.id.btSaveNote)
        etNoteName = findViewById(R.id.etNoteName)
        etNoteText = findViewById(R.id.etNoteText)

        btExitNote.setOnClickListener{
            backToNotesList()
        }

        btSaveNote.setOnClickListener{
            saveNote()
        }
    }


    // Help functions
    private fun backToNotesList(){
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    // takes new note and passes it to main activity
    private fun saveNote(){
        val intent = Intent()
        intent.putExtra("result", createNote()) // <== passes new note to main activity
        setResult(Activity.RESULT_OK, intent)
        finish()
        // ? display a success message
    }

    // Creates and returns new note item
    private fun createNote(): NoteClass {
        // -- room --
        // hardcode insertion for testing
        insertTestData()
        insertNoteToDatabase()
        deleteById(2)
        // -- room --
        return NoteClass(
            etNoteName.text.toString(),
            etNoteText.text.toString(),
            convertLongToTime(System.currentTimeMillis())
        )
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    // -- room --
    fun createNoteEntity(): NoteEntity {
        return NoteEntity(
            etNoteName.text.toString(),
            etNoteText.text.toString(),
            convertLongToTime(System.currentTimeMillis())
        )
    }

    fun insertNoteToDatabase(){
        NotesDB.getDatabase(this).noteDao().insert(createNoteEntity())
    }

    fun insertTestData() {
        for (i in 1..100) {
            val note = NoteEntity(
                "Note $i",
                "This is the text for note $i",
                convertLongToTime(System.currentTimeMillis())
            )
            NotesDB.getDatabase(this).noteDao().insert(note)
        }
    }

    fun deleteById(id: Int) {
        val note = NotesDB.getDatabase(this).noteDao().loadSingle(id)
        NotesDB.getDatabase(this).noteDao().delete(note)
    }

    fun editById(id: Int) {
        val newNote = NoteEntity("this is EDITED note")
        val oldNote = NotesDB.getDatabase(this).noteDao().loadSingle(id)
        NotesDB.getDatabase(this).noteDao().update(oldNote)
    }

    // -- room --
}

