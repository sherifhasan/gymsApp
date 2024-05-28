package com.example.gyms.presenation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gyms.domain.models.Gym
import com.example.gyms.domain.usecases.GetGymByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGymByIdUseCase: GetGymByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<Gym?>(null)
    private val errorHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    init {
        val gymId = savedStateHandle.get<String>("gym_id")?.toIntOrNull() ?: 0
        getGym(gymId)
    }

    val state: StateFlow<Gym?> get() = _state.asStateFlow()

    private fun getGym(id: Int) {
        viewModelScope.launch(errorHandler) {
            _state.update { getGymByIdUseCase(id) }
        }
    }
}