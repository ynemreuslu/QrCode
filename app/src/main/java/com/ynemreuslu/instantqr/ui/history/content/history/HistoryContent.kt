package com.ynemreuslu.instantqr.ui.history.content.history

import androidx.compose.runtime.Composable
import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ynemreuslu.instantqr.ui.history.content.card.QrCodeCard
import com.ynemreuslu.instantqr.ui.history.content.card.SwipeToDismissListItem
import com.ynemreuslu.instantqr.ui.history.content.sheet.BottomSheetHandle
import com.ynemreuslu.instantqr.ui.history.content.sheet.HistoryContentBottomSheet
import com.ynemreuslu.instantqr.model.QRCode
import com.ynemreuslu.instantqr.R
import com.ynemreuslu.instantqr.ui.history.content.card.QrCodeVsCreatorNot


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    viewModel: HistoryContentViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentSheet by remember { mutableStateOf<QRCode?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current


    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            }
        }
        uiState.qrcode.isEmpty() -> {
            QrCodeVsCreatorNot(stringResource(R.string.not_qrcode))
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .padding()
                    .fillMaxSize(),
                state = rememberLazyListState()
            ) {
                items(uiState.qrcode) { item ->
                    SwipeToDismissListItem(
                        onDelete = { viewModel.removeQrCode(item) },
                        content = {
                            QrCodeCard(qrCode = item, onQrCodeClick = {
                                currentSheet = item
                            })
                        }
                    )
                }
            }
        }
    }

    currentSheet?.let { qrcode ->
        ModalBottomSheet(
            onDismissRequest = { currentSheet = null },
            sheetState = sheetState,
            dragHandle = {
                BottomSheetHandle(
                    onClose = { currentSheet = null },
                    onShare = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, qrcode.qrCode)
                        }
                        context.startActivity(intent)
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,
            content = {
                BottomSheetContentQrCode(
                    qrcode, viewModel.generateQRCode(qrcode.qrCode)
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContentQrCode(qrCode: QRCode, bitmap: Bitmap) {
    val context = LocalContext.current
    HistoryContentBottomSheet(
        sectionTitle = stringResource(R.string.text_content),
        sectionDescription = qrCode.qrCode,
        buttonText = stringResource(R.string.open),
        onButtonClick = {
            val intentText = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, qrCode.qrCode)
            }
            context.startActivity(intentText)
        },
        bitmap = bitmap
    )
}