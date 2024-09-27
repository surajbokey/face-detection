package com.surajbokey.facedetection.tagging.domain.model

data class FaceTag(
    val faceId: String,
    val name: String
) {
    companion object {
        fun emptyTag(): FaceTag {
            return FaceTag("", "")
        }
    }
}
