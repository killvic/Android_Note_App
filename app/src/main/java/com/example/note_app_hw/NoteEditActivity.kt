package com.example.note_app_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.note_app_hw.Help_Functions.convertLongToDate
import com.example.note_app_hw.Help_Functions.convertLongToTime
import com.example.note_app_hw.Note_DB.NoteEntity
import com.example.note_app_hw.Note_DB.NotesDB

class NoteEditActivity : AppCompatActivity() {
    private lateinit var tvLastChanged: TextView
    private lateinit var btSaveNote: ImageButton
    private lateinit var etNoteName: EditText
    private lateinit var etNoteText: EditText
    private lateinit var btDeleteNote: ImageButton
    private lateinit var btBack: ImageButton
    private lateinit var ctFavorite: CheckBox
    var editMode: Boolean = false // false - Create mode, true - Edit Mode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        // views initialization
        btSaveNote = findViewById(R.id.btSaveNote)
        etNoteName = findViewById(R.id.etNoteName)
        etNoteText = findViewById(R.id.etNoteText)
        btDeleteNote = findViewById(R.id.btDeleteNote)
        btBack = findViewById(R.id.btBackToNotesList)
        ctFavorite = findViewById(R.id.cbFavorite)
        tvLastChanged = findViewById(R.id.tvLastChanged)
        var id = intent.getIntExtra("id", -1)
        editMode = mode(id)

        btBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        if (editMode) {
            var singleNote = NotesDB.getDatabase(this).noteDao().loadSingle(id)
            tvLastChanged.text = "last changed: " + convertLongToTime(singleNote.lastChange) + " " + convertLongToDate(singleNote.lastChange)
            etNoteName.setText(singleNote.name)
            etNoteText.setText(singleNote.text)
            ctFavorite.isChecked = singleNote.isFavorite

            btDeleteNote.setOnClickListener{
                NotesDB.getDatabase(this).noteDao().deleteById(id)
                onBackPressedDispatcher.onBackPressed()
            }
            btSaveNote.setOnClickListener{
                updateNoteInDatabase(id)
                onBackPressedDispatcher.onBackPressed()
            }
        }
        else {
            btSaveNote.setOnClickListener{
                insertNoteToDatabase()
                onBackPressedDispatcher.onBackPressed()
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

    // Takes information from edit textes and creates a NoteEntity
    private fun createNoteEntity(): NoteEntity {
        return NoteEntity(
            name = etNoteName.text.toString(),
            text = etNoteText.text.toString(),
            lastChange = System.currentTimeMillis(),
            isFavorite = ctFavorite.isChecked
        )
    }

    // updates db
    private fun updateNoteEntity(id: Int): NoteEntity {
        var noteForEditing = NotesDB.getDatabase(this).noteDao().loadSingle(id)
        noteForEditing.name = etNoteName.text.toString()
        noteForEditing.text = etNoteText.text.toString()
        noteForEditing.isFavorite = ctFavorite.isChecked
        noteForEditing.lastChange = System.currentTimeMillis()
        return noteForEditing
    }

    // Functions for new Note Initialization
    // <------------------------------------>
    // Room Database Functions

    // Calls for CreateNoteEntity() and adds returned entity to database
    private fun insertNoteToDatabase() {
        NotesDB.getDatabase(this).noteDao().insert(createNoteEntity())
    }

    private fun updateNoteInDatabase(id: Int) {
        NotesDB.getDatabase(this).noteDao().update(updateNoteEntity(id))
    }
    // Room Database Functions
    // <----------------------------------->
}


