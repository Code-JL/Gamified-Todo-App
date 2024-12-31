package com.example.gamifed_todo_app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamifed_todo_app.adapter.TaskAdapter
import com.example.gamifed_todo_app.databinding.ActivityMainBinding
import com.example.gamifed_todo_app.databinding.DialogAddTaskBinding
import com.example.gamifed_todo_app.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val taskAdapter = TaskAdapter { task -> viewModel.completeTask(task) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupAddTaskButton()
    }

    private fun setupRecyclerView() {
        binding.taskList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }
    }

    private fun setupObservers() {
        viewModel.allTasks.observe(this) { tasks ->
            taskAdapter.submitList(tasks)
        }

        viewModel.player.observe(this) { player ->
            binding.playerStats.text = "Level ${player.level} | XP: ${player.totalXp}"
        }
    }

    private fun setupAddTaskButton() {
        binding.addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(this))

        AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { dialog, _ ->
                val title = dialogBinding.taskTitleInput.text.toString()
                val xp = dialogBinding.xpValueInput.text.toString().toIntOrNull() ?: 0
                if (title.isNotBlank()) {
                    viewModel.addTask(title, xp)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
