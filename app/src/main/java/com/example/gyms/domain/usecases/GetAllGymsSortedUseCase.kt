package com.example.gyms.domain.usecases

import com.example.gyms.data.GymsRepository
import com.example.gyms.domain.models.Gym
import javax.inject.Inject

class GetAllGymsSortedUseCase @Inject constructor(
    private val gymsRepository: GymsRepository
) {
    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGyms().sortedBy { it.name }
    }
}