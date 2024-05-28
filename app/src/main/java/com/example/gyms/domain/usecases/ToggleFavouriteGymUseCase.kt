package com.example.gyms.domain.usecases

import com.example.gyms.data.GymsRepository
import com.example.gyms.domain.models.Gym
import javax.inject.Inject

class ToggleFavouriteGymUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getAllGymsSortedUseCase: GetAllGymsSortedUseCase
) {


    suspend operator fun invoke(id: Int, oldStateValue: Boolean): List<Gym> {
        gymsRepository.toggleFavouriteGym(id, oldStateValue.not())
        return getAllGymsSortedUseCase()
    }
}