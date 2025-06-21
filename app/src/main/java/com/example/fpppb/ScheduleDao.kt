package com.example.fpppb

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: ScheduleItem)

    @Delete
    suspend fun delete(schedule: ScheduleItem)

    @Update
    suspend fun update(schedule: ScheduleItem)

    @Query("SELECT * FROM schedule ORDER BY day")
    fun getAll(): Flow<List<ScheduleItem>>
}
