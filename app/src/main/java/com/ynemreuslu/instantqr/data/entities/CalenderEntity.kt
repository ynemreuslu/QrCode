package com.ynemreuslu.instantqr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("calender")
data class CalenderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val startDate: Long,
    val endDate: Long,
    val startTime: Long,
    val endTime: Long,
    var location: String?,
    val explanation: String?,
    val timestamp: Long
)