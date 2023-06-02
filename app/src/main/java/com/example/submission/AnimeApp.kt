package com.example.submission

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.submission.ui.navigation.NavigationItem
import com.example.submission.ui.navigation.Screen
import com.example.submission.ui.screen.detail.DetailScreen
import com.example.submission.ui.screen.favorite.FavoriteScreen
import com.example.submission.ui.screen.home.HomeScreen
import com.example.submission.ui.screen.profile.ProfileScreen

@Composable
fun AnimeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailAnime.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { animeId ->
                        navController.navigate(Screen.DetailAnime.createRoute(animeId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { animeId ->
                        navController.navigate(Screen.DetailAnime.createRoute(animeId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    profileImage = "https://media.licdn.com/dms/image/D5603AQFvmsRQLapPXg/profile-displayphoto-shrink_800_800/0/1681143904347?e=2147483647&v=beta&t=73rNOWB1s7Y33I_XtL_PFCxWuoYXlK0P0PAmHVsgzOU",
                    name = "Ahmad Chairiansyah",
                    email = "ahmad.chairiansyah@gmail.com"
                )
            }
            composable(
                route = Screen.DetailAnime.route,
                arguments = listOf(navArgument("animeId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("animeId") ?: -1L
                DetailScreen(
                    animeId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToFav = {
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = when (item.screen) {
                                Screen.Profile -> "about_page"
                                else -> item.title
                            }
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}