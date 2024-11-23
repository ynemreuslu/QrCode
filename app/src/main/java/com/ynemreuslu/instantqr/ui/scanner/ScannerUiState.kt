package com.ynemreuslu.instantqr.ui.scanner

import com.ynemreuslu.instantqr.model.QRCode

data class ScannerUiState(
    val hasCameraAccess: Boolean = false,
    val qrCode: QRCode? = null,
    val navigateToDetails: Boolean = false,
    val openGalleryDialog: Boolean = false,
)