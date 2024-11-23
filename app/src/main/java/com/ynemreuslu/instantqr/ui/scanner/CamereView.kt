package com.ynemreuslu.instantqr.ui.scanner

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

@Composable
fun CameraView(
    analyzer: ImageAnalysis.Analyzer,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }


    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var previewUseCase by remember { mutableStateOf<Preview?>(null) }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build().apply {
                setAnalyzer(cameraExecutor, analyzer)
            }

        cameraProvider.unbindAll()

        previewUseCase = Preview.Builder()
            .build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            imageAnalysis,
            previewUseCase
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
            cameraProviderFuture.get().unbindAll()
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { previewView }
    )
}