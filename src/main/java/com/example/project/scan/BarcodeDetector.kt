package com.example.project.scan

import android.annotation.SuppressLint
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage




internal class BarcodeDetector(private val listener: (code: String) -> Unit) : ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage: Image? = imageProxy.image
        Log.d("analyzer", "ok")
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener {barcodes ->
                    if (barcodes.count() != 0) {
                        barcodes.forEach{
                            Log.d("CODE",it.rawValue!!)
                            listener(it.rawValue!!)
                        }
                        mediaImage.close()
                    }
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    it.message?.let { it1 -> Log.d(TAG, it1) }
                }
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        //listener("scanning...")
                        Log.d(TAG,"Terminé avec succes")
                    }else{
                        Log.d(TAG,"Terminé sans succes")
                    }
                    imageProxy.close()
                    //scanner.close()
                }
        }
    }
    companion object {
        //private const val CHANNEL_ID = "1703"
        private const val TAG = "barcodedetector1"
        //private const val REQUEST_CODE_PERMISSIONS = 10
        //private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}