package dev.eder.architecturecomponents.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(val title: String, val description: String, val priority: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}