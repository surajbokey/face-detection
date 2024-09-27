package com.surajbokey.facedetection.tagging.domain

import com.surajbokey.facedetection.tagging.domain.model.FaceTag
import kotlinx.coroutines.flow.Flow

interface FaceTagRepository {
    suspend fun saveFaceTag(faceTag: FaceTag)
    fun getFaceTag(faceId: String): Flow<FaceTag>
}