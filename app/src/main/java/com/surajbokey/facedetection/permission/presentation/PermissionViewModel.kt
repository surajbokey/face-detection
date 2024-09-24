package com.surajbokey.facedetection.permission.presentation

import androidx.lifecycle.ViewModel
import com.surajbokey.facedetection.permission.domain.PermissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val permissionRepository: PermissionRepository
) : ViewModel() {

    fun hasGalleryPermission(): Boolean {
        return permissionRepository.hasGalleryPermission()
    }

    fun requestGalleryPermission() {
        permissionRepository.requestGalleryPermission()
    }
}