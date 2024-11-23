package com.ynemreuslu.instantqr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("email")
data class EmailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val address: String,
    val subject: String?,
    val body: String?,
    val timestamp: Long,
)