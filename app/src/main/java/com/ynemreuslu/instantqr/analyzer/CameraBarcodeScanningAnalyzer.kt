package com.ynemreuslu.instantqr.analyzer

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


@SuppressLint("UnsafeOptInUsageError")
class CameraBarcodeScanningAnalyzer(
    private val onBarcodeDetected: (barcodes: MutableList<Barcode>, width: Int, height: Int) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder().build()
    private val scanner = BarcodeScanning.getClient(options)
    private var hasDetectedBarcode = false

    override fun analyze(imageProxy: ImageProxy) {
        if (hasDetectedBarcode) {
            imageProxy.close()
            return
        }
        val image = imageProxy.image
        if (image != null) {
            val imageValue = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            scanner.process(imageValue)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        hasDetectedBarcode = true
                        onBarcodeDetected(barcodes, imageValue.height, imageValue.width)
                    }
                }
                .addOnFailureListener { failure ->
                    failure.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}