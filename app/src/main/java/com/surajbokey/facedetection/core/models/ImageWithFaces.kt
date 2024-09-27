package com.surajbokey.facedetection.core.models

import android.graphics.RectF
import android.net.Uri

data class ImageWithFaces(
    val uri: Uri,
    val boundingBoxes: List<RectF?>? = null
)