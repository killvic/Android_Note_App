package com.example.note_app_hw.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntity::class],
    version = 1 // изменить чтобы создать новую базу
    //exportSchema = false
)
abstract class NotesDB : RoomDatabase() {

    abstract fun noteDao(): NoteDAO

    companion object {

        @Volatile
        private var instance: NotesDB? = null

        fun getDatabase(context: Context): NotesDB {
            if(instance == null){
                synchronized(this){
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context): NotesDB {
            return Room.databaseBuilder(
                context.applicationContext,
                NotesDB::class.java,
                "notes_db"
            )
                // позволяет выполнять запросы к БД в главном потоке
                .allowMainThreadQueries()
                // удаляет текушую базу если её схема не совпадает с новой схемой
                // после изменения какого-нибудь Entity, либо после создания нового Entity
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}