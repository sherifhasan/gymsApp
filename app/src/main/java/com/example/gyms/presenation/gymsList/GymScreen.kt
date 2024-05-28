package com.example.gyms.presenation.gymsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gyms.domain.models.Gym


@Composable
fun GymScreen(
    state: GymsScreenState,
    onItemClicked: (Int) -> Unit,
    onFavourite: (Int, Boolean) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(
            content = {
                items(count = state.gyms.size) { index ->
                    GymListItem(
                        gym = state.gyms[index],
                        onFavourite = { id, isFavourite -> onFavourite(id, isFavourite) },
                        onItemClicked = { id ->
                            onItemClicked(id)
                        }
                    )
                }
            },
        )
        if (state.isLoading)
            CircularProgressIndicator(
                progress = 0.89f,
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        state.error?.let { error ->
            Text(text = error)
        }
    }
}

@Composable
fun GymListItem(gym: Gym, onFavourite: (Int, Boolean) -> Unit, onItemClicked: (Int) -> Unit) {
    val icon = if (gym.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ), modifier = Modifier
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
        contentDescription = contentDescription, modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            }, colorFilter = ColorFilter.tint(color = Color.White)
    )
}

@Composable
fun GymAddressDetails(gym: Gym, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = gym.name, style = TextStyle(color = Color.Magenta, fontSize = 18.sp))
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.5f
            )
        ) {
            Text(text = gym.place)
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
    }
}
