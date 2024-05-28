package com.example.gyms.presenation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gyms.domain.models.Gym
import com.example.gyms.domain.usecases.GetGymByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGymByIdUseCase: GetGymByIdUseCase
) : ViewModel() {
    var state by mutableStateOf<Gym?>(null)
    private val errorHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    init {
        val gymId = savedStateHandle.get<String>("gym_id")?.toIntOrNull() ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch(errorHandler) {
            state = getGymByIdUseCase(id)
        }
    }
}