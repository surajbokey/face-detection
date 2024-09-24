package com.surajbokey.facedetection.gallery.domain.models

import android.graphics.Bitmap
import android.net.Uri

interface GalleryRepository {
    suspend fun getGalleryImages(): List<GalleryImage>
    suspend fun loadBitmap(uri: Uri): Bitmap
}