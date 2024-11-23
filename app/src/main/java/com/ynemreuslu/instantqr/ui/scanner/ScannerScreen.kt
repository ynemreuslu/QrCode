package com.ynemreuslu.instantqr.ui.scanner

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.ynemreuslu.instantqr.analyzer.CameraBarcodeScanningAnalyzer
import com.ynemreuslu.instantqr.model.QRCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.ynemreuslu.instantqr.R

@Composable
fun ScannerScreen(
    viewModel: ScannerScreenViewModel = hiltViewModel(),
    hostState: SnackbarHostState,
    onDetails: (qrCode: QRCode) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showCameraPermissionDialog by remember { mutableStateOf(false) }
    val galleryLauncher = setupGalleryLauncher(viewModel, context)
    val message = stringResource(R.string.go_to_settings_for_camera_permission)
    val scope = rememberCoroutineScope()
    val label = stringResource(R.string.open)

    val cameraPermissionLauncher = setupCameraPermissionLauncher(
        viewModel,
        hostState,
        label,
        message,
        scope
    )

    if (uiState.navigateToDetails) {
        onDetails(uiState.qrCode!!)
        viewModel.onNavigatedToDetails()
    }

    HandleCameraPermission(
        context = context,
        cameraPermissionLauncher = cameraPermissionLauncher,
        showCameraPermissionDialog = showCameraPermissionDialog,
        onDialogDismiss = { showCameraPermissionDialog = false },
        onDialogConfirm = { showCameraPermissionDialog = true }
    )

    ScannerContent(
        galleryLauncher = galleryLauncher,
        viewModel = viewModel
    )

    if (uiState.openGalleryDialog) {
        GalleryNotAlertDialog(
            title = stringResource(R.string.error),
            message = stringResource(R.string.qr_code_not_found),
            buttonText = stringResource(R.string.okay),
            onDismiss = { viewModel.dismissGalleryDialog() }
        )
    }
}


@Composable
fun ScannerContent(
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    viewModel: ScannerScreenViewModel
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val detectedBarcodes = remember { mutableStateListOf<Barcode>() }

    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        CameraView(
            lifecycleOwner = lifecycleOwner,
            analyzer = CameraBarcodeScanningAnalyzer { barcodes, _, _ ->
                detectedBarcodes.clear()
                detectedBarcodes.addAll(barcodes)

                barcodes.forEach { barcode ->
                    barcode.rawValue?.let { qrCodeValue ->
                        val qrCode = QRCode(qrCode = qrCodeValue)
                        viewModel.insertQrCode(qrCode)
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Button(
                onClick = { galleryLauncher.launch("image/*") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color.White),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.from_photo),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun HandleCameraPermission(
    context: Context,
    cameraPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    showCameraPermissionDialog: Boolean,
    onDialogDismiss: () -> Unit,
    onDialogConfirm: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                context.getActivity(),
                CAMERA
            )
        ) {
            onDialogConfirm()
        } else {
            cameraPermissionLauncher.launch(CAMERA)
        }
    }

    if (showCameraPermissionDialog) {
        CameraPermissionExplanationDialog(
            onConfirm = {
                cameraPermissionLauncher.launch(CAMERA)
                onDialogDismiss()
            },
            onDismiss = onDialogDismiss
        )
    }
}

@Composable
fun setupGalleryLauncher(
    viewModel: ScannerScreenViewModel,
    context: Context
): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = viewModel.getBitmapFromUri(context, it)
            bitmap?.let { selectedImage ->
                viewModel.analyzeImage(selectedImage)
            }
        }
    }
}

@Composable
fun setupCameraPermissionLauncher(
    viewModel: ScannerScreenViewModel,
    snackbarHostState: SnackbarHostState,
    label: String,
    message: String,
    scope: CoroutineScope,

    ): ManagedActivityResultLauncher<String, Boolean> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.onPermissionChange(CAMERA, isGranted)
        } else {
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message,
                    actionLabel = label,
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}


@Composable
fun CameraPermissionExplanationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.camera_permission_required),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = stringResource(R.string.camera_permission_explanation),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        icon = {
            Icon(
                Icons.Filled.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.continue_button),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.dismiss_button),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
fun GalleryNotAlertDialog(
    title: String,
    message: String,
    buttonText: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(6.dp),
        confirmButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                Text(text = buttonText)
            }
        }
    )
}


fun Context.getActivity(): Activity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    throw IllegalStateException("Permissions must be requested within an activity context")
}