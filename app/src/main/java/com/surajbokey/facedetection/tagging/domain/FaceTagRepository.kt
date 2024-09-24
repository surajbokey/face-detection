package com.surajbokey.facedetection.tagging.domain

import kotlinx.coroutines.flow.Flow

interface FaceTagRepository {
    suspend fun saveFaceTag(faceId: String, name: String)
    fun getFaceTag(faceId: String): Flow<String?>
}