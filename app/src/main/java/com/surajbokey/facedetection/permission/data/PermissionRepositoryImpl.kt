package com.surajbokey.facedetection.permission.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.surajbokey.facedetection.permission.domain.PermissionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionRepository {
    override fun hasGalleryPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            context, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    override fun requestGalleryPermission() {

    }
}