package com.example.note_app_hw.Note_DB
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.note_app_hw.note_package.NoteClass

@Dao
interface NoteDAO {
    @Insert (onConflict = OnConflictStrategy.IGNORE) // .IGNORE is what to do if same id is passed
    fun insert(note: NoteEntity)

    @Delete()
    fun delete(note: NoteEntity)

    @Update // edit
    fun update(note: NoteEntity)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun readAllNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes_table WHERE id=:id ")
    fun loadSingle(id: Int): NoteEntity

}