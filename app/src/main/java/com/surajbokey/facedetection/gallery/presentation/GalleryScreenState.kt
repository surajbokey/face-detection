package com.surajbokey.facedetection.gallery.presentation

import com.surajbokey.facedetection.core.models.ImageWithFaces

sealed interface GalleryScreenState {
    data object Loading : GalleryScreenState
    data class Data(val images: List<ImageWithFaces>) : GalleryScreenState
    data class Error(val error: String) : GalleryScreenState
}