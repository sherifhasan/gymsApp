package com.example.gyms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.gyms.domain.models.Gym
import com.example.gyms.presenation.SemanticsDescription
import com.example.gyms.presenation.gymsList.GymScreen
import com.example.gyms.presenation.gymsList.GymsScreenState
import com.example.gyms.ui.theme.GymAppTheme
import org.junit.Rule
import org.junit.Test

class GymScreenTest {

    @get: Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {
        testRule.setContent {
            GymAppTheme {
                GymScreen(state = GymsScreenState(gyms = emptyList(), isLoading = true),
                    onItemClicked = {},
                    onFavourite = { _, _ -> })
            }
        }
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_SCREEN_LOADING)
            .assertIsDisplayed()
    }

    @Test
    fun loadedContentState_isActive() {

        val gymsList = arrayListOf(Gym(1, "One gym", "Munich", true))

        testRule.setContent {
            GymAppTheme {
                GymScreen(state = GymsScreenState(gyms = gymsList, isLoading = false),
                    onItemClicked = {},
                    onFavourite = { _, _ -> })
            }
        }
        testRule.onNodeWithText(gymsList[0].name).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_SCREEN_LOADING)
            .assertDoesNotExist()
    }

    @Test
    fun errorState_isActive() {

        val error = "Failed to load gyms list"

        testRule.setContent {
            GymAppTheme {
                GymScreen(state = GymsScreenState(gyms = emptyList(), isLoading = false, error),
                    onItemClicked = {},
                    onFavourite = { _, _ -> })
            }
        }
        testRule.onNodeWithText(error).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_SCREEN_LOADING)
            .assertDoesNotExist()
    }

    @Test
    fun onItemClick_IdPassedCorrectly() {
        val gymsList = arrayListOf(Gym(1, "One gym", "Munich", true))
        val gym = gymsList.first()
        testRule.setContent {
            GymAppTheme {
                GymScreen(state = GymsScreenState(gyms = gymsList, isLoading = false),
                    onItemClicked = {
                        assert(it == gym.id)
                    },
                    onFavourite = { _, _ -> })
            }
        }
        testRule.onNodeWithText(gym.name).performClick()
    }

    @Test
    fun onFavouriteItem_clicked() {
        val gymsList = arrayListOf(Gym(1, "One gym", "Munich", true))
        val gym = gymsList.first()
        testRule.setContent {
            GymAppTheme {
                GymScreen(state = GymsScreenState(gyms = gymsList, isLoading = false),
                    onItemClicked = {},
                    onFavourite = { id, isFavourite ->
                        assert(id == gym.id)
                        assert(isFavourite)
                    })
            }
        }
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_SCREEN_FAVOURITE_ICON)
            .performClick()
    }
}