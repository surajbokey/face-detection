package com.surajbokey.facedetection.detection.domain.model

import android.graphics.RectF
import android.net.Uri
import java.security.MessageDigest

data class DetectedFace(
    val boundingBox: RectF,
    val imageUri: Uri
) {
    val faceId: String = generateFaceId()

    private fun generateFaceId(): String {
        val data = "${imageUri}_${boundingBox.left}_${boundingBox.top}_${boundingBox.right}_${boundingBox.bottom}"
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(data.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}