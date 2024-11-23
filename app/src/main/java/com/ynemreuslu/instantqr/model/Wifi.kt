package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.WifiEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Wifi(
    val id: Long = 0,
    val wifiName: String,
    val wifiPassword: String?,
    val wifiType: String,
    val timestamp: Long
)

fun WifiEntity.asWifiModel() = Wifi(
    id = id,
    wifiName = wifiName,
    wifiPassword = wifiPassword,
    wifiType = wifiType,
    timestamp = timestamp
)

fun Wifi.asWifiEntityModel() = WifiEntity(
    id = id,
    wifiName = wifiName,
    wifiPassword = wifiPassword,
    wifiType = wifiType,
    timestamp = timestamp
)

fun Flow<List<WifiEntity>>.mapToWifiFlow(): Flow<List<Wifi>> {
    return this.map { wifiTypeList ->
        wifiTypeList.map { it.asWifiModel() }
    }
}