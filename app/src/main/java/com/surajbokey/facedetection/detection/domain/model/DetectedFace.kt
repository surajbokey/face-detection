package com.surajbokey.facedetection.detection.domain.model

import android.graphics.RectF
import android.net.Uri

data class DetectedFace(
    val boundingBox: RectF,
    val imageUri: Uri
) {
    val faceId: String = generateFaceId()

    private fun generateFaceId(): String {
        return "${imageUri}_${boundingBox.left}_${boundingBox.top}_${boundingBox.right}_${boundingBox.bottom}"
    }
}