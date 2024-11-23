package com.ynemreuslu.instantqr.data.repository

import com.ynemreuslu.instantqr.data.dao.QRCodeDao
import com.ynemreuslu.instantqr.model.QRCode
import com.ynemreuslu.instantqr.model.asQRCodeEntity
import com.ynemreuslu.instantqr.model.mapToQRCodeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QRCodeRepositoryImpl @Inject constructor(
    private val dao: QRCodeDao
) : QRCodeRepository {

    override suspend fun insertQRCode(qrCode: QRCode) =
        dao.insertQRCode(qrCode = qrCode.asQRCodeEntity())

    override suspend fun deleteQRCode(qrCode: QRCode) =
        dao.deleteQRCode(qrCode = qrCode.asQRCodeEntity())

    override fun fetchAllQRCodes(): Flow<List<QRCode>> =
        dao.fetchAllQRCodes().mapToQRCodeFlow()

    override suspend fun deleteQRCodeId(qrCodeId: Long) =
        dao.deleteQRCodeId(qrCodeId)
}