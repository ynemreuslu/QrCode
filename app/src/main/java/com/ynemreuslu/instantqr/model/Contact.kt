package com.ynemreuslu.instantqr.model


import com.ynemreuslu.instantqr.data.entities.ContactEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Contact(
    val id: Long = 0,
    val name: String?,
    val number: String?,
    val email: String?,
    val timestamp: Long,
)

fun ContactEntity.asContractModel() = Contact(id, name, number, email, timestamp)

fun Contact.asContractEntity() = ContactEntity(id, name, number, email, timestamp)

fun Flow<List<ContactEntity>>.mapToContactFlow(): Flow<List<Contact>> {
    return this.map { contactList ->
        contactList.map { it.asContractModel() }
    }
}