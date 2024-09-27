package com.surajbokey.facedetection.gallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surajbokey.facedetection.gallery.domain.GetImagesUseCase
import com.surajbokey.facedetection.gallery.presentation.GalleryScreenState.Data
import com.surajbokey.facedetection.gallery.presentation.GalleryScreenState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    private val _uiStates = MutableStateFlow<GalleryScreenState>(Loading)
    val uiStates = _uiStates.asStateFlow()
    var data = Data(emptyList())

    init {

        viewModelScope.launch(Dispatchers.IO) {
            getImagesUseCase().collect {
                data = Data(data.images + it)
                _uiStates.value = data
            }
        }
    }
}