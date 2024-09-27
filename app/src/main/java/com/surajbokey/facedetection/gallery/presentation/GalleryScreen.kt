package com.surajbokey.facedetection.gallery.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.surajbokey.facedetection.core.models.ImageWithFaces
import com.surajbokey.facedetection.gallery.domain.models.GalleryImage
import com.surajbokey.facedetection.gallery.presentation.GalleryScreenState.Data
import com.surajbokey.facedetection.gallery.presentation.GalleryScreenState.Error
import com.surajbokey.facedetection.gallery.presentation.GalleryScreenState.Loading

@Composable
fun GalleryScreen(
    onImageSelected: (Uri) -> Unit,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()

    when (uiState) {
        is Data -> ShowImages((uiState as Data).images, onImageSelected)
        is Error -> ShowError((uiState as Error).error)
        Loading -> ShowLoading()
    }
}

@Composable
fun ShowLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ShowImages(images: List<ImageWithFaces>, onImageSelected: (Uri) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(8.dp)
    ) {
        items(images) { image ->
            GalleryImageItem(image, onImageSelected)
        }
    }
}

@Composable
fun ShowError(errorText: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(errorText, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun GalleryImageItem(
    image: ImageWithFaces,
    onImageSelected: (Uri) -> Unit
) {
    Image(
        painter = rememberAsyncImagePainter(model = image.uri),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clickable { onImageSelected(image.uri) }
            .width(200.dp)
            .height(200.dp)
    )
}
