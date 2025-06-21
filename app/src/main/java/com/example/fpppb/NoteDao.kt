package com.example.fpppb

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<NoteItem>>

    @Insert
    suspend fun insert(note: NoteItem)

    @Delete
    suspend fun delete(note: NoteItem)

    @Update
    suspend fun update(note: NoteItem)  // ← wajib ada ini!
}
