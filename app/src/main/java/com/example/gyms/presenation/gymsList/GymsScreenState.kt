package com.example.gyms.presenation.gymsList

import com.example.gyms.domain.models.Gym

data class GymsScreenState(val gyms: List<Gym>, val isLoading: Boolean, val error: String? = null)