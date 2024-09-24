package com.surajbokey.facedetection.permission.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.surajbokey.facedetection.permission.domain.PermissionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionRepository {
    override fun hasGalleryPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_IMAGES
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    override fun requestGalleryPermission() {
        // Implementation will be in the PermissionScreen using Accompanist Permissions
    }
}