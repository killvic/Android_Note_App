package com.example.note_app_hw.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_hw.Data.Database.NoteEntity
import com.example.note_app_hw.Data.Database.NotesDB
import com.example.note_app_hw.domain.models.NoteClass
import com.example.note_app_hw.R
import com.example.note_app_hw.presentation.adapters.RecyclerViewAdapterForFolder

class FavoriteFolderActivity : AppCompatActivity() {
    private val adapter: RecyclerViewAdapterForFolder = RecyclerViewAdapterForFolder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_folder)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewFavorite)
        recyclerView.adapter = adapter

        adapter.onClick = {
            val intent = Intent(this@FavoriteFolderActivity, NoteEditActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }
        recyclerView.layoutManager =
            GridLayoutManager(this, 3)
    }

    override fun onStart() { // database logic
        super.onStart()
        val entityList = NotesDB.getDatabase(this).noteDao().readAllNotes()
        adapter.setData(entityToClassConverter(entityList))
    }

    private fun entityToClassConverter(entityList: List<NoteEntity>): List<NoteClass> {
        var noteClassList: List<NoteClass> = entityList.map { entity ->
            NoteClass(
                id = entity.id ?: 0,
                lastChange = entity.lastChange,
                name = entity.name,
                text = entity.text,
                favorite = entity.favorite
            )
        }
        return noteClassList.filter {it.favorite}
    }
}