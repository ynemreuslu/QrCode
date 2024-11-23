package com.ynemreuslu.instantqr.ui.scanner

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynemreuslu.instantqr.analyzer.GalleryBarcodeScanningAnalyzer
import com.ynemreuslu.instantqr.data.repository.QRCodeRepository
import com.ynemreuslu.instantqr.model.QRCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerScreenViewModel @Inject constructor(
    private val qrCodeRepository: QRCodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> get() = _uiState.asStateFlow()

    fun insertQrCode(qrCode: QRCode) {
        viewModelScope.launch {
            try {
                qrCodeRepository.insertQRCode(qrCode)
                updateUiStateAfterQrInsert(qrCode)
            } catch (e: Exception) {
                Log.e("QRCodeInsertError", "Error inserting QR code: ${e.message}", e)
            }
        }
    }

    private val barcodeScanningAnalyzer = GalleryBarcodeScanningAnalyzer { barcodes, _, _ ->
        if (barcodes.isNotEmpty()) {
            barcodes.firstOrNull()?.displayValue?.let { qrCodeValue ->
                val qrCode = QRCode(qrCode = qrCodeValue)
                insertQrCode(qrCode)
            }
        } else {
            showGalleryDialog()
        }
    }

    fun analyzeImage(bitmap: Bitmap) {
        barcodeScanningAnalyzer.analyze(bitmap)
    }

    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            Log.e("BitmapError", "Error retrieving bitmap: ${e.message}", e)
            null
        }
    }

    fun onPermissionChange(permission: String, isGranted: Boolean) {
        when (permission) {
            Manifest.permission.CAMERA -> {
                updateUiState { it.copy(hasCameraAccess = isGranted) }
            }
            else -> Log.e("PermissionError", "Unexpected permission: $permission")
        }
    }

    fun showGalleryDialog() {
        updateUiState { it.copy(openGalleryDialog = true) }
    }

    fun dismissGalleryDialog() {
        updateUiState { it.copy(openGalleryDialog = false) }
    }

    fun onNavigatedToDetails() {
        updateUiState { it.copy(navigateToDetails = false) }
    }

    private fun updateUiStateAfterQrInsert(qrCode: QRCode) {
        updateUiState {
            it.copy(
                qrCode = qrCode,
                navigateToDetails = true,
                openGalleryDialog = false
            )
        }
    }

    private fun updateUiState(update: (ScannerUiState) -> ScannerUiState) {
        _uiState.update(update)
    }
}
