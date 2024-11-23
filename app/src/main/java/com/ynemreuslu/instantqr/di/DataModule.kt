package com.ynemreuslu.instantqr.di

import com.ynemreuslu.instantqr.data.repository.CreatorRepository
import com.ynemreuslu.instantqr.data.repository.CreatorRepositoryImpl
import com.ynemreuslu.instantqr.data.repository.QRCodeRepository
import com.ynemreuslu.instantqr.data.repository.QRCodeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindQrCodeRepository(
        qrCodeRepositoryImpl: QRCodeRepositoryImpl
    ): QRCodeRepository

    @Binds
    abstract fun bindCreatorRepository(
        creatorRepositoryImpl: CreatorRepositoryImpl
    ): CreatorRepository
}