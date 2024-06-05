package com.example.gyms

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
                GymScreen(
                    state = GymsScreenState(gyms = emptyList(), isLoading = true),
                    onItemClicked = {},
                    onFavourite = { _, _ -> Boolean })
            }
        }
        testRule.onNodeWithContentDescription(SemanticsDescription.GYM_SCREEN_LOADING)
            .assertIsDisplayed()
    }
}