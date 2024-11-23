package com.ynemreuslu.instantqr.model

import com.ynemreuslu.instantqr.data.entities.SmsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Sms(
    val id: Long = 0,
    val number: String,
    val message: String,
    val timestamp: Long,
)

fun SmsEntity.asSmsModel() = Sms(id, number, message,timestamp)

fun Sms.asSmsEntityModel() = SmsEntity(id, number, message,timestamp)

fun Flow<List<SmsEntity>>.mapToSmsFlow(): Flow<List<Sms>> {
    return this.map { smsList ->
        smsList.map { it.asSmsModel() }
    }
}