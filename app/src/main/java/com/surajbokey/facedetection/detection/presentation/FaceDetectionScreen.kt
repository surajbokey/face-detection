package com.surajbokey.facedetection.detection.presentation

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.surajbokey.facedetection.tagging.domain.model.FaceTag
import com.surajbokey.facedetection.tagging.presentation.FaceTagDialog

@Composable
fun FaceDetectionScreen(
    imageUri: String?,
    viewModel: FaceDetectionViewModel = hiltViewModel()
) {
    val imageBitmap by viewModel.imageBitmap.collectAsState()
    val detectedFaces by viewModel.detectedFaces.collectAsState()
    val selectedFaceId by viewModel.selectedFaceId.collectAsState()
    val faceTags by viewModel.faceTags.collectAsState()

    val selectedFaceTag by viewModel
        .getFaceTag(selectedFaceId ?: "")
        .collectAsState(FaceTag.emptyTag())

    LaunchedEffect(imageUri) {
        imageUri?.let {
            viewModel.loadImageAndDetectFaces(Uri.parse(it))
            println("load=======")
        }
    }

    LaunchedEffect(detectedFaces) {
        viewModel.loadFaceTags()
        println("load.....")
    }

    if (imageBitmap == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        FaceImageItem(
            modifier = Modifier.fillMaxSize(),
            bitmap = imageBitmap!!,
            detectedFaces = detectedFaces,
            faceTags = faceTags,
            onFaceClicked = { faceId -> viewModel.onFaceClicked(faceId) }
        )
    }

    if (selectedFaceId != null) {
        FaceTagDialog(
            faceId = selectedFaceId!!,
            initialName = selectedFaceTag.name,
            onDismiss = { viewModel.onFaceClicked(null) },
            onSave = { name ->
                viewModel.saveFaceTag(selectedFaceId!!, name)
            }
        )
    }
}