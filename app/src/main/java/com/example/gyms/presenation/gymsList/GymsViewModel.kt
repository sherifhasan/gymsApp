package com.example.gyms.presenation.gymsList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gyms.domain.usecases.GetInitialGymsUseCase
import com.example.gyms.domain.usecases.ToggleFavouriteGymUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GymsViewModel @Inject constructor(
    private val gymsUseCase: GetInitialGymsUseCase,
    private val toggleFavouriteGymUseCase: ToggleFavouriteGymUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        GymsScreenState(
            gyms = emptyList(),
            isLoading = true,
        )
    )

    val state: StateFlow<GymsScreenState> get() = _state.asStateFlow()

    private val errorHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _state.value = _state.value.copy(error = throwable.message, isLoading = false)
        }

    init {
        getGymsList()
    }


    private fun getGymsList() {
        viewModelScope.launch(errorHandler + Dispatchers.Main) {
            val receivedGyms = gymsUseCase()
            _state.update { it.copy(gyms = receivedGyms, isLoading = false) }
        }
    }

    fun toggleFavouriteState(gymId: Int, oldValue: Boolean) {
        viewModelScope.launch {
            val updatedGymsList = toggleFavouriteGymUseCase(gymId, oldValue)
            _state.update { it.copy(gyms = updatedGymsList) }
        }
    }
}