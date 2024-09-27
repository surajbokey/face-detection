package com.surajbokey.facedetection.gallery.domain

import android.graphics.Bitmap
import android.net.Uri
import com.surajbokey.facedetection.gallery.domain.models.GalleryImage

interface GalleryRepository {
    suspend fun getGalleryImages(): List<GalleryImage>
    suspend fun getBitmap(uri: Uri): Bitmap
}