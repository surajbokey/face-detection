package com.surajbokey.facedetection.detection.data

import android.graphics.Bitmap
import android.net.Uri
import com.surajbokey.facedetection.detection.domain.FaceDetectionRepository
import com.surajbokey.facedetection.detection.domain.model.DetectedFace
import javax.inject.Inject


class FaceDetectionRepositoryImpl @Inject constructor(
    private val faceDetectionDataSource: FaceDetectionDataSource
) : FaceDetectionRepository {
    override suspend fun detectFaces(bitmap: Bitmap, uri: Uri): List<DetectedFace> {
        return faceDetectionDataSource.detectFaces(bitmap, uri)
    }
}