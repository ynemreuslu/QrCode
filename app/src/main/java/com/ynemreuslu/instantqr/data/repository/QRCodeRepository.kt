package com.ynemreuslu.instantqr.data.repository

import com.ynemreuslu.instantqr.model.QRCode
import kotlinx.coroutines.flow.Flow

interface QRCodeRepository {

    suspend fun insertQRCode(qrCode: QRCode)
    suspend fun deleteQRCode(qrCode: QRCode)
    fun fetchAllQRCodes(): Flow<List<QRCode>>
    suspend fun deleteQRCodeId(qrCodeId: Long)

}