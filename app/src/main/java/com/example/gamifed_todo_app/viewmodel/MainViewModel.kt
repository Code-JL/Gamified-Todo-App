package com.example.gamifed_todo_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskquestxp.data.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val taskDao = database.taskDao()
    private val playerDao = database.playerDao()

    val allTasks = taskDao.getAllTasks().asLiveData()
    val player = playerDao.getPlayer().asLiveData()

    fun addTask(title: String, xpValue: Int) {
        viewModelScope.launch {
            taskDao.insertTask(Task(title = title, xpValue = xpValue))
        }
    }

    fun completeTask(task: Task) {
        viewModelScope.launch {
            task.isCompleted = true
            taskDao.updateTask(task)

            val currentPlayer = player.value ?: Player()
            currentPlayer.totalXp += task.xpValue
            currentPlayer.level = (currentPlayer.totalXp / 100) + 1
            playerDao.updatePlayer(currentPlayer)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}
