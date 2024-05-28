package com.example.gyms.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocalGym::class],
    version = 2,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {
    abstract val dao: GymsDAO

    companion object {
        @Volatile
        private var daoInstance: GymsDAO? = null
        private fun buildDatabase(context: Context): GymsDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                GymsDatabase::class.java,
                "gyms_database"
            ).fallbackToDestructiveMigration().build()

        fun getDaoInstance(context: Context): GymsDAO {
            synchronized(this) {
                if (daoInstance == null) {
                    daoInstance = buildDatabase(context).dao
                }
                return daoInstance as GymsDAO
            }
        }
    }
}