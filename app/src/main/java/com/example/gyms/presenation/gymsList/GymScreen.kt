package com.example.gyms.presenation.gymsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.gyms.domain.models.Gym
import com.example.gyms.ui.theme.GymAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymScreen(
    state: GymsScreenState,
    onItemClicked: (Int) -> Unit,
    onFavourite: (Int, Boolean) -> Unit
) {
    GymAppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    title = { Text("Gyms App", style = MaterialTheme.typography.headlineMedium) }
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    content = {
                        items(count = state.gyms.size) { index ->
                            GymListItem(
                                gym = state.gyms[index],
                                onFavourite = { id, isFavourite -> onFavourite(id, isFavourite) },
                                onItemClicked = { id -> onItemClicked(id) }
                            )
                        }
                    },
                )
                if (state.isLoading) {
                    CircularProgressIndicator(
                        progress = 0.89f,
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
                state.error?.let { error ->
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun GymListItem(gym: Gym, onFavourite: (Int, Boolean) -> Unit, onItemClicked: (Int) -> Unit) {
    val icon = if (gym.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClicked(gym.id) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            DefaultIcon(
                Icons.Filled.Place,
                modifier = Modifier.weight(0.15f),
                contentDescription = "Gym Place Icon"
            )
            GymAddressDetails(gym, Modifier.weight(0.70f))
            DefaultIcon(icon, Modifier.weight(0.15f), contentDescription = "Favourite Icon") {
                onFavourite(gym.id, gym.isFavourite)
            }
        }
    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() },
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun GymAddressDetails(gym: Gym, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = gym.name,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
        )
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ) {
            Text(
                text = gym.place, style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
    }
}
