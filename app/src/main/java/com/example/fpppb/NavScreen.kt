package com.example.fpppb

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

object NavScreen {
    val Home = BottomNavItem("Beranda", "home", Icons.Default.Home)
    val Schedule = BottomNavItem("Jadwal", "schedule", Icons.Default.DateRange)
    val Task = BottomNavItem("Tugas", "task", Icons.Default.List)
    val Notes = BottomNavItem("Catatan", "notes", Icons.Default.Edit)
    val Profile = BottomNavItem("Profil", "profile", Icons.Default.Person)
}
