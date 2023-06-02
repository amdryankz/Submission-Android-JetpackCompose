package com.example.submission.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailAnime : Screen("home/{animeId}") {
        fun createRoute(animeId: Long) = "home/$animeId"
    }
}
