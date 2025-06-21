package com.example.fpppb

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.fpppb.TaskItem


@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskItem>>

    @Insert
    suspend fun insert(task: TaskItem)

    @Delete
    suspend fun delete(task: TaskItem)

    @Query("SELECT * FROM tasks WHERE isDone = 0")
    fun getActiveTasks(): Flow<List<TaskItem>>

    @Query("SELECT * FROM tasks WHERE isDone = 1")
    fun getCompletedTasks(): Flow<List<TaskItem>>

    @Update
    suspend fun updateTask(task: TaskItem)
}
