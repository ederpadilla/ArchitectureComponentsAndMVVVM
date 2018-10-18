package dev.eder.architecturecomponents.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Room
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.os.AsyncTask


@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class PopulateDbAsyncTask (db: NoteDatabase) : AsyncTask<Void, Void, Void>() {
        private val noteDao: NoteDao

        init {
            noteDao = db.noteDao()
        }

        override fun doInBackground(vararg voids: Void): Void? {
            noteDao.insert(Note("Title 1", "Description 1", 1))
            noteDao.insert(Note("Title 2", "Description 2", 2))
            noteDao.insert(Note("Title 3", "Description 3", 3))
            return null
        }
    }

    companion object {

        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java, "note_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            }
            return instance!!
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance!!).execute()
            }
        }
    }
}