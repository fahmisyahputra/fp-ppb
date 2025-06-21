package com.example.fpppb

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "schedule")
data class ScheduleItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val day: String,
    val time: String
)

// Fungsi ekstensi untuk bantu sorting berdasarkan waktu aktual
fun ScheduleItem.getNextScheduleTimestamp(): Long {
    val dayMap = mapOf(
        "Senin" to Calendar.MONDAY,
        "Selasa" to Calendar.TUESDAY,
        "Rabu" to Calendar.WEDNESDAY,
        "Kamis" to Calendar.THURSDAY,
        "Jumat" to Calendar.FRIDAY,
        "Sabtu" to Calendar.SATURDAY
    )

    val cal = Calendar.getInstance().apply {
        val parts = this@getNextScheduleTimestamp.time.split(":")
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0

        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        val today = get(Calendar.DAY_OF_WEEK)
        val target = dayMap[this@getNextScheduleTimestamp.day] ?: Calendar.MONDAY
        val daysUntilTarget = (target - today + 7) % 7
        add(Calendar.DAY_OF_YEAR, daysUntilTarget)
    }

    return cal.timeInMillis
}
