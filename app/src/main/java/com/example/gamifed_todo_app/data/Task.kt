package com.example.gamifed_todo_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val xpValue: Int,
    var isCompleted: Boolean = false
)
