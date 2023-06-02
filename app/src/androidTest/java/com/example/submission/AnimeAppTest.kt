package com.example.submission

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.submission.model.FakeAnimeDataSource
import com.example.submission.ui.navigation.Screen
import com.example.submission.ui.theme.SubmissionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AnimeAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                AnimeApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("AnimeList").performScrollToIndex(9)
        composeTestRule.onNodeWithText(FakeAnimeDataSource.dummyAnime[8].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailAnime.route)
        composeTestRule.onNodeWithText(FakeAnimeDataSource.dummyAnime[8].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithText("Favorite").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText("Profile").performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithText("Home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("AnimeList").performScrollToIndex(9)
        composeTestRule.onNodeWithText(FakeAnimeDataSource.dummyAnime[8].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailAnime.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(FakeAnimeDataSource.dummyAnime[2].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailAnime.route)
        composeTestRule.onNodeWithText("Add to Favorite").performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        composeTestRule.onNodeWithText("Favorite").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
    }
}