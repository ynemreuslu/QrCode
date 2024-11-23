package com.ynemreuslu.instantqr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_code")
data class QRCodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val qrCode: String
)