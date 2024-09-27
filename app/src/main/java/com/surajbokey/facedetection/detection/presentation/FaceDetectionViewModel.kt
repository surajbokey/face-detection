package com.surajbokey.facedetection.detection.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surajbokey.facedetection.detection.domain.FaceDetectionRepository
import com.surajbokey.facedetection.detection.domain.model.DetectedFace
import com.surajbokey.facedetection.gallery.domain.GalleryRepository
import com.surajbokey.facedetection.tagging.domain.FaceTagRepository
import com.surajbokey.facedetection.tagging.domain.model.FaceTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaceDetectionViewModel @Inject constructor(
    private val faceDetectionRepository: FaceDetectionRepository,
    private val faceTagRepository: FaceTagRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _detectedFaces = MutableStateFlow<List<DetectedFace>>(emptyList())
    val detectedFaces: StateFlow<List<DetectedFace>> = _detectedFaces.asStateFlow()

    private val _selectedFaceId = MutableStateFlow<String?>(null)
    val selectedFaceId: StateFlow<String?> = _selectedFaceId.asStateFlow()

    private val _faceTags = MutableStateFlow<Map<String, String>>(emptyMap())
    val faceTags: StateFlow<Map<String, String>> = _faceTags.asStateFlow()

    private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap: StateFlow<Bitmap?> = _imageBitmap.asStateFlow()

    fun loadFaceTags() {
        viewModelScope.launch {
            val tags = mutableMapOf<String, String>()
            _detectedFaces.value.forEach { face ->
                launch {
                    getFaceTag(face.faceId).collect { name ->
                        tags[face.faceId] = name.name
                        println("name === ${name.name}")
                        _faceTags.value = tags
                    }
                }
            }
        }
    }

    fun loadImageAndDetectFaces(uri: Uri) {
        viewModelScope.launch {
            val bitmap = galleryRepository.getBitmap(uri)
            _imageBitmap.value = bitmap
            val faces = faceDetectionRepository.detectFaces(bitmap, uri)
            _detectedFaces.value = faces
        }
    }

    fun onFaceClicked(faceId: String?) {
        _selectedFaceId.value = faceId
    }

    fun saveFaceTag(faceId: String, name: String) {
        viewModelScope.launch {
            faceTagRepository.saveFaceTag(FaceTag(faceId, name))
            _selectedFaceId.value = null
        }
    }

    fun getFaceTag(faceId: String): Flow<FaceTag> {
        return faceTagRepository.getFaceTag(faceId)
    }
}