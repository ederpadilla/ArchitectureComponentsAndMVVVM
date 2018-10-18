package dev.eder.architecturecomponents.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Update
import android.arch.persistence.room.Dao


@Dao
interface NoteDao {

    @get:Query("SELECT * FROM note_table ORDER BY priority DESC")
    val allNotes: LiveData<List<Note>>

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()
}