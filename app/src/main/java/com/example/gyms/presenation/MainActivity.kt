package com.example.gyms.presenation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.gyms.presenation.details.GymDetailsScreen
import com.example.gyms.presenation.gymsList.GymScreen
import com.example.gyms.presenation.gymsList.GymsViewModel
import com.example.gyms.ui.theme.GymAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                Surface(Modifier.fillMaxSize()) {
                    GymsApp()
                }
            }
        }
    }
}

@Composable
private fun GymsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {
        composable("gyms") {
            val vm: GymsViewModel = hiltViewModel()
            GymScreen(
                state = vm.state.value,
                onFavourite = { id, oldValue -> vm.toggleFavouriteState(id, oldValue) },
                onItemClicked = { id -> navController.navigate("gyms/$id") })

        }
        composable(
            "gyms/{gym_id}",
            arguments = listOf(navArgument("gym_id") { NavType.IntType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://www.gymsapp.com/details/{gym_id}"
                })
        ) {
            GymDetailsScreen()
        }
    }
}