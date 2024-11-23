package com.ynemreuslu.instantqr.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ynemreuslu.instantqr.data.entities.CalenderEntity
import com.ynemreuslu.instantqr.data.entities.ContactEntity
import com.ynemreuslu.instantqr.data.entities.EmailEntity
import com.ynemreuslu.instantqr.data.entities.LocationEntity
import com.ynemreuslu.instantqr.data.entities.PhoneEntity
import com.ynemreuslu.instantqr.data.entities.SmsEntity
import com.ynemreuslu.instantqr.data.entities.TextEntity
import com.ynemreuslu.instantqr.data.entities.UrlEntity
import com.ynemreuslu.instantqr.data.entities.WifiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertText(text: TextEntity)

    @Delete
    suspend fun removeText(text: TextEntity)

    @Query("SELECT * from text ORDER BY id DESC")
    fun fetchAllTexts(): Flow<List<TextEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUrl(qrCode: UrlEntity)

    @Delete
    suspend fun removeUrl(qrCode: UrlEntity)

    @Query("SELECT * from url ORDER BY id DESC")
    fun fetchAllUrl(): Flow<List<UrlEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity)

    @Delete
    suspend fun removeContact(contact: ContactEntity)

    @Query("SELECT * from contact ORDER BY id DESC")
    fun fetchAllContacts(): Flow<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhone(phone: PhoneEntity)

    @Delete
    suspend fun removePhone(phone: PhoneEntity)

    @Query("SELECT * from phone ORDER BY id DESC")
    fun fetchAllPhones(): Flow<List<PhoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSms(sms: SmsEntity)

    @Delete
    suspend fun removeSms(sms: SmsEntity)

    @Query("SELECT * from sms ORDER BY id DESC")
    fun fetchAllSms(): Flow<List<SmsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmail(email: EmailEntity)

    @Delete
    suspend fun removeEmail(email: EmailEntity)

    @Query("SELECT * from email ORDER BY id DESC")
    fun fetchAllEmails(): Flow<List<EmailEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Delete
    suspend fun removeLocation(location: LocationEntity)

    @Query("SELECT * from location ORDER BY id DESC")
    fun fetchAllLocations(): Flow<List<LocationEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWifi(wifi: WifiEntity)

    @Query("SELECT * FROM wifi")
    fun fetchAllWifi(): Flow<List<WifiEntity>>

    @Delete
    suspend fun deleteWifi(wifi: WifiEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalender(calendar: CalenderEntity)

    @Delete
    suspend fun deleteCalender(calendar: CalenderEntity)

    @Query("SELECT * from calender ORDER BY id DESC")
    fun fetchAllCalender(): Flow<List<CalenderEntity>>

}