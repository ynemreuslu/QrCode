package com.ynemreuslu.instantqr.di

import com.ynemreuslu.instantqr.data.dao.CreatorDao
import com.ynemreuslu.instantqr.data.dao.QRCodeDao
import com.ynemreuslu.instantqr.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesQrCodeDao(
        database: AppDatabase
    ): QRCodeDao = database.qrCodeDao()

    @Provides
    fun providesCreatorDao(
        database: AppDatabase
    ): CreatorDao = database.creatorDao()
}