package com.surajbokey.facedetection.detection.data

import android.graphics.Bitmap
import android.net.Uri
import com.surajbokey.facedetection.detection.domain.model.DetectedFace
import com.surajbokey.facedetection.detection.FaceDetectorHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FaceDetectionDataSourceImpl @Inject constructor(
    private val helper: FaceDetectorHelper
) : FaceDetectionDataSource {
    override suspend fun detectFaces(bitmap: Bitmap, uri: Uri): List<DetectedFace> {

        return withContext(Dispatchers.Default) {
            helper.detectImage(bitmap)?.let { detections ->
                return@withContext detections.detections()
                    .map { it.boundingBox() }
                    .map { DetectedFace(it, uri) }
                    .toList()
            } ?: run {
                emptyList()
            }
        }
    }
}


