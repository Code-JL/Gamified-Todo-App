package com.example.gamifed_todo_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey val id: Int = 1,
    var totalXp: Int = 0,
    var level: Int = 1
)
