package com.surajbokey.facedetection.gallery.data

import android.graphics.Bitmap
import android.net.Uri

interface GalleryDataSource {
    suspend fun getGalleryImageUris(): List<Uri>
    suspend fun getBitmap(uri: Uri): Bitmap
}