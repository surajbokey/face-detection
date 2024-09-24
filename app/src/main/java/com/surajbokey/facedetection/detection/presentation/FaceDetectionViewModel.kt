package com.surajbokey.facedetection.detection.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surajbokey.facedetection.detection.domain.FaceDetectionRepository
import com.surajbokey.facedetection.detection.domain.model.DetectedFace
import com.surajbokey.facedetection.gallery.domain.models.GalleryRepository
import com.surajbokey.facedetection.tagging.domain.FaceTagRepository
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

    private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap: StateFlow<Bitmap?> = _imageBitmap.asStateFlow()

    fun loadImageAndDetectFaces(uri: Uri) {
        viewModelScope.launch {
            val bitmap = galleryRepository.loadBitmap(uri)
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
            faceTagRepository.saveFaceTag(faceId, name)
            _selectedFaceId.value = null
        }
    }

    fun getFaceTag(faceId: String): Flow<String?> {
        return faceTagRepository.getFaceTag(faceId)
    }
}