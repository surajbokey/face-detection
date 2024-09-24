package com.surajbokey.facedetection.gallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surajbokey.facedetection.gallery.domain.models.GalleryImage
import com.surajbokey.facedetection.gallery.domain.models.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _galleryImages = MutableStateFlow<List<GalleryImage>>(emptyList())
    val galleryImages: StateFlow<List<GalleryImage>> = _galleryImages.asStateFlow()

    init {
        viewModelScope.launch {
            val images = galleryRepository.getGalleryImages()
            _galleryImages.value = images
        }
    }
}