package com.ynemreuslu.instantqr.data.repository

import com.ynemreuslu.instantqr.model.Calender
import com.ynemreuslu.instantqr.model.Contact
import com.ynemreuslu.instantqr.model.Email
import com.ynemreuslu.instantqr.model.Location
import com.ynemreuslu.instantqr.model.Phone
import com.ynemreuslu.instantqr.model.Sms
import com.ynemreuslu.instantqr.model.Text
import com.ynemreuslu.instantqr.model.Url
import com.ynemreuslu.instantqr.model.Wifi
import kotlinx.coroutines.flow.Flow

interface CreatorRepository {

    suspend fun insertUrl(url: Url)
    suspend fun removeUrl(url: Url)
    fun fetchAllUrl(): Flow<List<Url>>

    suspend fun insertText(text: Text)
    suspend fun removeText(text: Text)
    fun fetchAllTexts(): Flow<List<Text>>

    suspend fun insertContact(contact: Contact)
    suspend fun removeContact(contact: Contact)
    fun fetchAllContacts(): Flow<List<Contact>>

    suspend fun insertPhone(phone: Phone)
    suspend fun removePhone(phone: Phone)
    fun fetchAllPhones(): Flow<List<Phone>>

    suspend fun insertSms(sms: Sms)
    suspend fun removeSms(sms: Sms)
    fun fetchAllSms(): Flow<List<Sms>>

    suspend fun insertEmail(email: Email)
    suspend fun removeEmail(email: Email)
    fun fetchAllEmails(): Flow<List<Email>>

    suspend fun insertLocation(location: Location)
    suspend fun removeLocation(location: Location)
    fun fetchAllLocations(): Flow<List<Location>>

    suspend fun insertWifi(wifi: Wifi)
    suspend fun removeWifi(wifi: Wifi)
    fun fetchAllWifi(): Flow<List<Wifi>>

    suspend fun insertCalender(calender: Calender)
    suspend fun removeCalender(calender: Calender)
    fun fetchAllCalender(): Flow<List<Calender>>
}