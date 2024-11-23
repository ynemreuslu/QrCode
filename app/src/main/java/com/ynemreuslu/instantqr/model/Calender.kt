package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.CalenderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

data class Calender(
    val id: Long = 0,
    val calenderTitle: String,
    val startDate: Long,
    val finishDate: Long,
    val startTime: Long,
    val finishTime: Long,
    var location: String?,
    val explanation: String?,
    val timestamp: Long,
)

fun CalenderEntity.asCalenderModel() = Calender(
    id,
    title,
    startDate,
    endDate,
    startTime,
    endTime,
    location,
    explanation,
    timestamp
)

fun Calender.asCalenderEntity() = CalenderEntity(
    id,
    calenderTitle,
    startDate,
    finishDate,
    startTime,
    finishTime,
    location,
    explanation,
    timestamp
)

fun Flow<List<CalenderEntity>>.mapToCalenderFlow(): Flow<List<Calender>> {
    return this.map { calenderList ->
        calenderList.map { it.asCalenderModel() }
    }
}