package com.ynemreuslu.instantqr.analyzer

import android.graphics.Bitmap
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class GalleryBarcodeScanningAnalyzer(
    private val onBarcodeDetected: (barcodes: MutableList<Barcode>, width: Int, height: Int) -> Unit
) {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    fun analyze(bitmap: Bitmap) {
        val imageValue = InputImage.fromBitmap(bitmap, 0)

        scanner.process(imageValue)
            .addOnSuccessListener { barcodes ->
                onBarcodeDetected(barcodes, imageValue.height, imageValue.width)
            }
            .addOnFailureListener { failure ->
                failure.printStackTrace()
            }
    }
}