package com.ynemreuslu.instantqr.model

import android.os.Parcelable
import com.ynemreuslu.instantqr.data.entities.QRCodeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.parcelize.Parcelize

@Parcelize
data class QRCode(
    val id: Long = 0,
    val qrCode: String
) : Parcelable

fun QRCodeEntity.asQRCodeModel(): QRCode = QRCode(id, qrCode)

fun QRCode.asQRCodeEntity(): QRCodeEntity = QRCodeEntity(id, qrCode)

fun Flow<List<QRCodeEntity>>.mapToQRCodeFlow(): Flow<List<QRCode>> {
    return this.map { qrCodeList ->
        qrCodeList.map { it.asQRCodeModel() }
    }
}