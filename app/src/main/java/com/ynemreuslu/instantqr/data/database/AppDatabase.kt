package com.ynemreuslu.instantqr.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ynemreuslu.instantqr.data.entities.CalenderEntity
import com.ynemreuslu.instantqr.data.entities.ContactEntity
import com.ynemreuslu.instantqr.data.entities.EmailEntity
import com.ynemreuslu.instantqr.data.entities.LocationEntity
import com.ynemreuslu.instantqr.data.entities.PhoneEntity
import com.ynemreuslu.instantqr.data.entities.QRCodeEntity
import com.ynemreuslu.instantqr.data.entities.SmsEntity
import com.ynemreuslu.instantqr.data.entities.TextEntity
import com.ynemreuslu.instantqr.data.entities.UrlEntity
import com.ynemreuslu.instantqr.data.entities.WifiEntity
import com.ynemreuslu.instantqr.data.dao.CreatorDao
import com.ynemreuslu.instantqr.data.dao.QRCodeDao

@Database(
    entities = [
        QRCodeEntity::class,
        CalenderEntity::class,
        ContactEntity::class,
        EmailEntity::class,
        LocationEntity::class,
        SmsEntity::class,
        WifiEntity::class,
        TextEntity::class,
        UrlEntity::class,
        PhoneEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QRCodeDao
    abstract fun creatorDao(): CreatorDao
}