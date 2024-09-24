package com.surajbokey.facedetection.gallery.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.surajbokey.facedetection.gallery.domain.models.GalleryImage

@Composable
fun GalleryScreen(
    onImageSelected: (Uri) -> Unit,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val galleryImages by viewModel.galleryImages.collectAsState()

    if (galleryImages.isEmpty()) {
        Text("No images found.")
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(8.dp)
        ) {
            items(galleryImages) { image ->
                GalleryImageItem(image, onImageSelected)
            }
        }
    }
}

@Composable
fun GalleryImageItem(
    image: GalleryImage,
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

@Composable
fun GalleryImageItem(imageUri: Uri, onImageSelected: (Uri) -> Unit, modifier: Modifier = Modifier, targetSize: Dp = 150.dp) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUri)
            .size(
                targetSize.value.toInt(),
                targetSize.value.toInt()
            )
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(targetSize)
            .width(targetSize)
            .clickable { onImageSelected(imageUri) }
    )
}