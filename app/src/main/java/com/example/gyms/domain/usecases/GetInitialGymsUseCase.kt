package com.example.gyms.domain.usecases

import com.example.gyms.data.GymsRepository
import com.example.gyms.domain.models.Gym
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private val getAllGymsSortedUseCase: GetAllGymsSortedUseCase,
    private val gymsRepository: GymsRepository
) {
    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getAllGymsSortedUseCase()
    }
}