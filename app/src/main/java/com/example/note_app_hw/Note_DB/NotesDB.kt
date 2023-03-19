package com.example.note_app_hw.Note_DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [NoteEntity::class],
    version = 2
    // 1 to 2 changes: Added isFavorite parameter for notes
    //exportSchema = false
)
abstract class NotesDB : RoomDatabase() {

    abstract fun noteDao(): NoteDAO

    companion object {

        @Volatile
        private var instance: NotesDB? = null

        fun getDatabase(context: Context): NotesDB{
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
                .addMigrations(MIGRATION_1_2)
                // позволяет выполнять запросы к БД в главном потоке
                .allowMainThreadQueries()
                // удаляет текушую базу если её схема не совпадает с новой схемой
                // после изменения какого-нибудь Entity, либо после создания нового Entity
                //.fallbackToDestructiveMigration()
                .build()
        }
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notes_table ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}