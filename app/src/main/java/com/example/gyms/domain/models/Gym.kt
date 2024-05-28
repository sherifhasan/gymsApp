package com.example.gyms.domain.models


data class Gym(
    val id: Int,
    val name: String,
    val place: String,
    val isOpen: Boolean,
    val isFavourite: Boolean = false
)