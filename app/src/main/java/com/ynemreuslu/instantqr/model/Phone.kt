package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.PhoneEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Phone(
    val id: Long = 0,
    val number: String,
    val timestamp: Long,
)

fun PhoneEntity.asPhoneModel() = Phone(
    id = id,
    number = number,
    timestamp = timestamp
)

fun Phone.asPhoneEntity() = PhoneEntity(
    id = id,
    number = number,
    timestamp = timestamp,
)

fun Flow<List<PhoneEntity>>.mapToPhoneFlow(): Flow<List<Phone>> {
    return this.map { phoneList ->
        phoneList.map { it.asPhoneModel() }
    }
}