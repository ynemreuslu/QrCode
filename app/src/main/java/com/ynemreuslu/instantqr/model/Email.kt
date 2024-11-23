package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.EmailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Email(
    val id: Long = 0,
    val address: String,
    val subject: String?,
    val body: String?,
    val timestamp: Long,
)

fun EmailEntity.asEmailModel() = Email(id, address, subject, body,timestamp)

fun Email.asEmailEntity() = EmailEntity(id, address, subject, body,timestamp)

fun Flow<List<EmailEntity>>.mapToEmailFlow(): Flow<List<Email>> {
    return this.map { emailList ->
        emailList.map { it.asEmailModel() }
    }
}