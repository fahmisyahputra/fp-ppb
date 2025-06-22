package com.example.fpppb

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")

    object Home : Screen("home")
    object Schedule : Screen("schedule")
    object Task : Screen("task")
    object Notes : Screen("notes")
    object Profile : Screen("profile")
}
