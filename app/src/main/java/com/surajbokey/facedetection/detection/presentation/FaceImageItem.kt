package com.surajbokey.facedetection.detection.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surajbokey.facedetection.detection.domain.model.DetectedFace

@Composable
fun FaceImageItem(
    modifier: Modifier,
    bitmap: Bitmap,
    detectedFaces: List<DetectedFace>,
    faceTags: Map<String, String>, // Map of faceId to tagName
    onFaceClicked: (String) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val imageWidth = bitmap.width.toFloat()
        val imageHeight = bitmap.height.toFloat()

        val containerWidth = constraints.maxWidth.toFloat()
        val containerHeight = constraints.maxHeight.toFloat()

        val widthScale = containerWidth / imageWidth
        val heightScale = containerHeight / imageHeight

        val scale = minOf(widthScale, heightScale)

        val scaledImageWidth = imageWidth * scale
        val scaledImageHeight = imageHeight * scale

        val horizontalPadding = (containerWidth - scaledImageWidth) / 2
        val verticalPadding = (containerHeight - scaledImageHeight) / 2

        val density = LocalDensity.current

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Gallery Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            detectedFaces.forEach { face ->
                val boundingBox = face.boundingBox

                val left = boundingBox.left * scale + horizontalPadding
                val top = boundingBox.top * scale + verticalPadding
                val right = boundingBox.right * scale + horizontalPadding
                val bottom = boundingBox.bottom * scale + verticalPadding

                drawRect(
                    color = Color.Red,
                    topLeft = Offset(left, top),
                    size = Size(right - left, bottom - top),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        detectedFaces.forEach { face ->
            val boundingBox = face.boundingBox

            val left = boundingBox.left * scale + horizontalPadding
            val top = boundingBox.top * scale + verticalPadding
            val right = boundingBox.right * scale + horizontalPadding
            val bottom = boundingBox.bottom * scale + verticalPadding

            val tagName = faceTags[face.faceId] ?: ""

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = left.toInt(),
                            y = top.toInt()
                        )
                    }
                    .size(
                        width = (right - left).dp,
                        height = (bottom - top).dp
                    )
                    .clickable {
                        onFaceClicked(face.faceId)
                    }
            ) {
                Text(
                    text = tagName, color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}