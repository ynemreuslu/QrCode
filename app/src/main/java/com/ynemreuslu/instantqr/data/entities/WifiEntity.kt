package com.ynemreuslu.instantqr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wifi")
data class WifiEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val wifiName: String,
    val wifiPassword: String?,
    val wifiType: String,
    val timestamp: Long
)