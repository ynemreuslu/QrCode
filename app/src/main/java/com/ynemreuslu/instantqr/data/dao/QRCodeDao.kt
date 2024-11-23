package com.ynemreuslu.instantqr.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ynemreuslu.instantqr.data.entities.QRCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QRCodeDao {

    @Insert
    suspend fun insertQRCode(qrCode: QRCodeEntity)

    @Delete
    suspend fun deleteQRCode(qrCode: QRCodeEntity)

    @Query("SELECT * from qr_code ORDER BY id DESC")
    fun fetchAllQRCodes(): Flow<List<QRCodeEntity>>

    @Query("DELETE FROM qr_code WHERE id = :qrCodeId")
    suspend fun deleteQRCodeId(qrCodeId: Long)

}