package com.example.fpppb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ScheduleItem::class, TaskItem::class, NoteItem::class],
    version = 4 // ← Jangan lupa update versinya kalau sebelumnya 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao


    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "schedule_db"  // Nama database tetap bisa pakai ini
                )
                    .fallbackToDestructiveMigration() // tambahkan ini
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
