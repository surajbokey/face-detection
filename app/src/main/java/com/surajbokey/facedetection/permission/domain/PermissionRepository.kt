package com.surajbokey.facedetection.permission.domain

interface PermissionRepository {
    fun hasGalleryPermission(): Boolean
    fun requestGalleryPermission()
}