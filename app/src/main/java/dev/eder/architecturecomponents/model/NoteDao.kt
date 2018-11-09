package dev.eder.architecturecomponents.model

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Dao


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