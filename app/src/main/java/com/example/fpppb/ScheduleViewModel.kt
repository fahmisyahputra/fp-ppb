package com.example.fpppb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).scheduleDao()

    val scheduleList: StateFlow<List<ScheduleItem>> = dao.getAll()
        .map { list -> list.sortedBy { it.getNextScheduleTimestamp() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun add(subject: String, day: String, time: String) {
        viewModelScope.launch {
            dao.insert(ScheduleItem(subject = subject, day = day, time = time))
        }
    }

    fun delete(item: ScheduleItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

    fun update(item: ScheduleItem) {
        viewModelScope.launch {
            dao.update(item)
        }
    }
}
