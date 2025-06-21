package com.example.fpppb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.fpppb.TaskItem


class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).taskDao()

    val taskList = dao.getAll().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val activeTasks = dao.getActiveTasks().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val completedTasks = dao.getCompletedTasks().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun add(title: String, notes: String, deadline: Long) {
        viewModelScope.launch {
            dao.insert(TaskItem(title = title, notes = notes, deadline = deadline))
        }
    }

    fun delete(task: TaskItem) {
        viewModelScope.launch {
            dao.delete(task)
        }
    }

    fun update(task: TaskItem) {
        viewModelScope.launch {
            dao.updateTask(task)
        }
    }

    fun toggleTaskStatus(task: TaskItem) {
        viewModelScope.launch {
            dao.updateTask(task.copy(isDone = !task.isDone))
        }
    }
}
