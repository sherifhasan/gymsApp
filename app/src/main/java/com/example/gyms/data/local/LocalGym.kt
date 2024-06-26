package com.example.gyms.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gyms")
data class LocalGym(
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "gym_name")
    val name: String,
    val place: String,
    val isOpen: Boolean,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false
)