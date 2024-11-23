package com.ynemreuslu.instantqr.ui.history.content.sheet

import androidx.compose.runtime.Composable

@Composable
fun BottomSheetHandle(onClose: () -> Unit, onShare: () -> Unit) {
    DragHandle(
        onClose = onClose, onShare = onShare
    )
}
