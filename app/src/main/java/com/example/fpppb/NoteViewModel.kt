package com.example.fpppb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).noteDao()
    val noteList = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun add(title: String, content: String) {
        viewModelScope.launch {
            dao.insert(NoteItem(title = title, content = content))
        }
    }

    fun delete(note: NoteItem) {
        viewModelScope.launch {
            dao.delete(note)
        }
    }

    // ✅ Fungsi baru: update catatan
    fun update(note: NoteItem) {
        viewModelScope.launch {
            dao.update(note)
        }
    }
}
