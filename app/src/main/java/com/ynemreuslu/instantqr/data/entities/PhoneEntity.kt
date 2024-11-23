package com.ynemreuslu.instantqr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("phone")
data class PhoneEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val number: String,
    val timestamp: Long,
)