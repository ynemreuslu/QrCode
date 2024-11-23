package com.ynemreuslu.instantqr.data.repository

import com.ynemreuslu.instantqr.data.dao.CreatorDao
import com.ynemreuslu.instantqr.model.Calender
import com.ynemreuslu.instantqr.model.Contact
import com.ynemreuslu.instantqr.model.Email
import com.ynemreuslu.instantqr.model.Location
import com.ynemreuslu.instantqr.model.Phone
import com.ynemreuslu.instantqr.model.Sms
import com.ynemreuslu.instantqr.model.Text
import com.ynemreuslu.instantqr.model.Url
import com.ynemreuslu.instantqr.model.Wifi
import com.ynemreuslu.instantqr.model.asCalenderEntity
import com.ynemreuslu.instantqr.model.asContractEntity
import com.ynemreuslu.instantqr.model.asEmailEntity
import com.ynemreuslu.instantqr.model.asLocationEntity
import com.ynemreuslu.instantqr.model.asPhoneEntity
import com.ynemreuslu.instantqr.model.asSmsEntityModel
import com.ynemreuslu.instantqr.model.asTextEntity
import com.ynemreuslu.instantqr.model.asUrlEntityModel
import com.ynemreuslu.instantqr.model.asWifiEntityModel
import com.ynemreuslu.instantqr.model.mapToCalenderFlow
import com.ynemreuslu.instantqr.model.mapToContactFlow
import com.ynemreuslu.instantqr.model.mapToEmailFlow
import com.ynemreuslu.instantqr.model.mapToLocationFlow
import com.ynemreuslu.instantqr.model.mapToPhoneFlow
import com.ynemreuslu.instantqr.model.mapToSmsFlow
import com.ynemreuslu.instantqr.model.mapToTextFlow
import com.ynemreuslu.instantqr.model.mapToUrlFlow
import com.ynemreuslu.instantqr.model.mapToWifiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatorRepositoryImpl @Inject constructor(
    private val dao: CreatorDao
) : CreatorRepository {

    override suspend fun insertUrl(url: Url) =
        dao.insertUrl(url.asUrlEntityModel())

    override suspend fun removeUrl(url: Url) =
        dao.removeUrl(url.asUrlEntityModel())

    override fun fetchAllUrl(): Flow<List<Url>> =
        dao.fetchAllUrl().mapToUrlFlow()

    override suspend fun insertText(text: Text) =
        dao.insertText(text.asTextEntity())

    override suspend fun removeText(text: Text) =
        dao.removeText(text.asTextEntity())

    override fun fetchAllTexts(): Flow<List<Text>> =
        dao.fetchAllTexts().mapToTextFlow()

    override suspend fun insertContact(contact: Contact) =
        dao.insertContact(contact.asContractEntity())

    override suspend fun removeContact(contact: Contact) =
        dao.removeContact(contact.asContractEntity())

    override fun fetchAllContacts(): Flow<List<Contact>> =
        dao.fetchAllContacts().mapToContactFlow()

    override suspend fun insertPhone(phone: Phone) =
        dao.insertPhone(phone.asPhoneEntity())

    override suspend fun removePhone(phone: Phone) =
        dao.removePhone(phone.asPhoneEntity())

    override fun fetchAllPhones(): Flow<List<Phone>> =
        dao.fetchAllPhones().mapToPhoneFlow()

    override suspend fun insertSms(sms: Sms) =
        dao.insertSms(sms.asSmsEntityModel())

    override suspend fun removeSms(sms: Sms) =
        dao.removeSms(sms.asSmsEntityModel())

    override fun fetchAllSms(): Flow<List<Sms>> =
        dao.fetchAllSms().mapToSmsFlow()

    override suspend fun insertEmail(email: Email) =
        dao.insertEmail(email.asEmailEntity())

    override suspend fun removeEmail(email: Email) =
        dao.removeEmail(email.asEmailEntity())

    override fun fetchAllEmails(): Flow<List<Email>> =
        dao.fetchAllEmails().mapToEmailFlow()

    override suspend fun insertLocation(location: Location) =
        dao.insertLocation(location.asLocationEntity())

    override suspend fun removeLocation(location: Location) =
        dao.removeLocation(location.asLocationEntity())

    override fun fetchAllLocations(): Flow<List<Location>> =
        dao.fetchAllLocations().mapToLocationFlow()

    override suspend fun insertWifi(wifi: Wifi) =
        dao.insertWifi(wifi.asWifiEntityModel())

    override suspend fun removeWifi(wifi: Wifi) =
        dao.deleteWifi(wifi.asWifiEntityModel())

    override fun fetchAllWifi(): Flow<List<Wifi>> =
        dao.fetchAllWifi().mapToWifiFlow()

    override suspend fun insertCalender(calender: Calender) =
        dao.insertCalender(calender.asCalenderEntity())

    override suspend fun removeCalender(calender: Calender) =
        dao.deleteCalender(calender.asCalenderEntity())

    override fun fetchAllCalender(): Flow<List<Calender>> =
        dao.fetchAllCalender().mapToCalenderFlow()
}