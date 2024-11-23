package com.ynemreuslu.instantqr.ui.history.content.history

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.data.repository.QRCodeRepository
import com.ynemreuslu.instantqr.model.QRCode
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryContentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: QRCodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryContentUiState())
    val uiState: StateFlow<HistoryContentUiState> = _uiState.asStateFlow()

    init {
        fetchQrCodes()
    }

    private fun fetchQrCodes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.fetchAllQRCodes()
                .collect { qrCodes ->
                    _uiState.update {
                        it.copy(
                            qrcode = qrCodes,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun removeQrCode(qrCode: QRCode) {
        viewModelScope.launch {
            repository.deleteQRCode(qrCode)
            _uiState.update { currentState ->
                currentState.copy(qrcode = currentState.qrcode.filterNot { it.id == qrCode.id })
            }
        }
    }

    fun generateQRCode(
        text: String,
        width: Int = QR_CODE_WIDTH,
        height: Int = QR_CODE_HEIGHT
    ): Bitmap {
        val bitMatrix = MultiFormatWriter().encode(
            text,
            BarcodeFormat.QR_CODE,
            width,
            height
        )

        return Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565).apply {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    setPixel(x, y, if (bitMatrix[x, y]) QR_COLOR else BACKGROUND_COLOR)
                }
            }
        }
    }

    companion object {
        private const val QR_CODE_WIDTH = 600
        private const val QR_CODE_HEIGHT = 600
        private const val QR_COLOR = -0x1000000
        private const val BACKGROUND_COLOR = -0x1
    }
}