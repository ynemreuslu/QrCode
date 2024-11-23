package com.ynemreuslu.instantqr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
)