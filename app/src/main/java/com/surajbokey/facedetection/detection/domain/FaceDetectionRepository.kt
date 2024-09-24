package com.surajbokey.facedetection.detection.domain

import android.graphics.Bitmap
import android.net.Uri
import com.surajbokey.facedetection.detection.domain.model.DetectedFace

interface FaceDetectionRepository {
    suspend fun detectFaces(bitmap: Bitmap, uri: Uri): List<DetectedFace>
}