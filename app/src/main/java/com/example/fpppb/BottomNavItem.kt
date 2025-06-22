package com.example.fpppb

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

data class ScreenItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)
object AppScreen {
    val Home = ScreenItem("home", "Beranda", Icons.Default.Home)
    val Schedule = ScreenItem("schedule", "Jadwal", Icons.Default.DateRange)
    val Task = ScreenItem("task", "Tugas", Icons.Default.List)
    val Notes = ScreenItem("notes", "Catatan", Icons.Default.Edit)
    val Profile = ScreenItem("profile", "Profil", Icons.Default.Person)
}

