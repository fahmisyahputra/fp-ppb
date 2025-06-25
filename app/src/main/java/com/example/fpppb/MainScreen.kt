package com.example.fpppb

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        AppScreen.Home,
        AppScreen.Schedule,
        AppScreen.Task,
        AppScreen.Notes,
        AppScreen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item: ScreenItem ->
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
            startDestination = AppScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppScreen.Home.route) { HomeScreen(navController) }
            composable(AppScreen.Schedule.route) { ScheduleScreen() }
            composable(AppScreen.Task.route) { TaskScreen() }
            composable(AppScreen.Notes.route) { NotesScreen() }
            composable(AppScreen.Profile.route) { ProfileScreen() }
        }
    }
}
