package com.example.note_app_hw.Note_DB
import androidx.room.*

@Dao
interface NoteDAO {
    @Insert (onConflict = OnConflictStrategy.IGNORE) // .IGNORE is what to do if same id is passed
    fun insert(note: NoteEntity)

    @Insert
    fun insertAll(notes: List<NoteEntity>)

    @Delete()
    fun delete(note: NoteEntity)

    @Update // edit
    fun update(note: NoteEntity)

    @Query("SELECT * FROM notes_table ORDER BY lastChange DESC")
    fun readAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes_table WHERE id=:id")
    fun loadSingle(id: Int): NoteEntity

    // Extra Query
    @Query("DELETE FROM notes_table WHERE id=:id")
    fun deleteById(id: Int)
}