package com.surajbokey.facedetection.gallery.data

import android.graphics.Bitmap
import android.net.Uri
import com.surajbokey.facedetection.gallery.domain.models.GalleryImage
import com.surajbokey.facedetection.gallery.domain.models.GalleryRepository
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val galleryDataSource: GalleryDataSource
) : GalleryRepository {
    override suspend fun getGalleryImages(): List<GalleryImage> {
        return galleryDataSource.getGalleryImages().map { uri ->
            GalleryImage(uri)
        }
    }

    override suspend fun loadBitmap(uri: Uri): Bitmap {
        return galleryDataSource.loadBitmap(uri)
    }
}