package com.example.wishlistapp

//sealed means that nobody can inherit from it
sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AddScreen: Screen("add_screen")
}