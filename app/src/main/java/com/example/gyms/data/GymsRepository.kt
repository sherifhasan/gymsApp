package com.example.gyms.data

import com.example.gyms.data.remote.GymsApiService
import com.example.gyms.GymsApplication
import com.example.gyms.data.local.GymsDAO
import com.example.gyms.data.local.GymsDatabase
import com.example.gyms.data.local.LocalGym
import com.example.gyms.domain.models.Gym
import com.example.gyms.presenation.gymsList.LocalGymFavouriteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(private val apiService: GymsApiService,
                                         private val gymsDao: GymsDAO,) {

    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (ex: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong. No data was found, try connecting to internet.")
            }
        }
    }

    suspend fun getGyms(): List<Gym> {
        return withContext(Dispatchers.IO) {
            return@withContext gymsDao.getAll().map {
                Gym(it.id, it.name, it.place, it.isOpen, it.isFavourite)
            }

        }
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGyms = gymsDao.getFavouriteGyms()
        gymsDao.addAll(gyms.map { LocalGym(it.id, it.name, it.place, it.isOpen) })
        gymsDao.updateAll(favouriteGyms.map {
            LocalGymFavouriteState(
                id = it.id,
                isFavourite = true
            )
        })
    }

    suspend fun toggleFavouriteGym(gymId: Int, newFavouriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(LocalGymFavouriteState(id = gymId, isFavourite = newFavouriteState))
            return@withContext gymsDao.getAll()
        }

    suspend fun getGymByIdFromDatabase(id: Int): Gym {
        return gymsDao.getGym(id).let {
            Gym(
                id = it.id, name = it.name, place = it.place, isOpen = it.isOpen, it.isFavourite
            )
        }
    }
}