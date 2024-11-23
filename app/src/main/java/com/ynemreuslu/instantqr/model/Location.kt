package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Location(
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
)

fun LocationEntity.asLocationModel() = Location(
    id = id,
    latitude = latitude,
    longitude = longitude,
    timestamp = timestamp,
)

fun Location.asLocationEntity() = LocationEntity(
    id = id,
    latitude = latitude,
    longitude = longitude,
    timestamp=timestamp,
)

fun Flow<List<LocationEntity>>.mapToLocationFlow(): Flow<List<Location>> {
    return this.map { locationList ->
        locationList.map { it.asLocationModel() }
    }
}