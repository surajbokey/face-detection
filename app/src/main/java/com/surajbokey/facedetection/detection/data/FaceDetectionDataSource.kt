package com.surajbokey.facedetection.detection.data

import android.graphics.Bitmap
import android.net.Uri
import com.surajbokey.facedetection.detection.domain.model.DetectedFace

interface FaceDetectionDataSource {
    suspend fun detectFaces(bitmap: Bitmap, uri: Uri): List<DetectedFace>
}