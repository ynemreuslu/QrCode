package com.ynemreuslu.instantqr.ui.history.content.history

import com.ynemreuslu.instantqr.model.QRCode

data class HistoryContentUiState(
    val qrcode: List<QRCode> = emptyList(),
    val isLoading: Boolean = false,
)