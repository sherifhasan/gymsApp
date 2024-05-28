package com.example.gyms.presenation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gyms.domain.models.Gym

@Composable
fun GymDetailsScreen() {
    val vm: GymDetailsViewModel = hiltViewModel()
    val state =
        vm.state.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
    val item = state.value
    item?.let {
        DetailsScreen(item)
    }
}

@Composable
fun DetailsScreen(gym: Gym) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(380.dp),
                imageVector = Icons.Filled.Place,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = gym.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(0.9f)
                )
                Image(
                    imageVector = if (gym.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.1f)
                        .size(36.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Spacer(
                modifier = Modifier
                    .size(36.dp)
            )
            Text(
                text = gym.place, style = MaterialTheme.typography.bodyLarge
            )
            Spacer(
                modifier = Modifier
                    .size(36.dp)
            )
            Text(
                text = if (gym.isOpen) "Gym is opened" else "Gym is Closed",
                style = MaterialTheme.typography.bodyLarge,
                color = if (gym.isOpen) Color.Green else Color.Red
            )
        }
    }
}