package com.example.note_app_hw.Note_DB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel(application: Application): AndroidViewModel(application) {

   // private val readAllData: LiveData<List<NoteEntity>>
    //private val repository: NoteRepository

    init {
        val noteDAO = NotesDB.getDatabase(application).noteDao()
        //repository = NoteRepository(noteDAO)
        //readAllData = repository.readAllNotes
    }

    fun addNote(note: NoteEntity){
        viewModelScope.launch(Dispatchers.IO){
            //repository.addNote(note)
        }
    }
}
