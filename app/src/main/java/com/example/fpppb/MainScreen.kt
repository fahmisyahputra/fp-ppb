package com.example.fpppb

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        NavScreen.Home,
        NavScreen.Schedule,
        NavScreen.Task,
        NavScreen.Notes,
        NavScreen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item: BottomNavItem -> // ❗ Perjelas tipe data
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavScreen.Home.route) { HomeScreen(navController) }
            composable(NavScreen.Schedule.route) { ScheduleScreen() }
            composable(NavScreen.Task.route) { TaskScreen() }
            composable(NavScreen.Notes.route) { NotesScreen() }
            composable(NavScreen.Profile.route) { ProfileScreen() }
        }
    }
}

