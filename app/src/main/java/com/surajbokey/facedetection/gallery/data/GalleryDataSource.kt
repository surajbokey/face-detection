package com.surajbokey.facedetection.gallery.data

import android.graphics.Bitmap
import android.net.Uri

interface GalleryDataSource {
    suspend fun getGalleryImages(): List<Uri>
    suspend fun loadBitmap(uri: Uri): Bitmap
}