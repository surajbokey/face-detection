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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.surajbokey.facedetection.detection.domain.model.DetectedFace

@Composable
fun FaceImageItem(
    modifier: Modifier,
    bitmap: Bitmap,
    detectedFaces: List<DetectedFace>,
    onFaceClicked: (String) -> Unit
) {
    BoxWithConstraints(modifier) {
        val imageWidth = bitmap.width.toFloat()
        val imageHeight = bitmap.height.toFloat()

        val boxWidth = constraints.maxWidth.toFloat()
        val boxHeight = constraints.maxHeight.toFloat()

        val widthScale = boxWidth / imageWidth
        val heightScale = boxHeight / imageHeight

        val scale = minOf(widthScale, heightScale)

        val scaledImageWidth = imageWidth * scale
        val scaledImageHeight = imageHeight * scale

        val horizontalPadding = (boxWidth - scaledImageWidth) / 2
        val verticalPadding = (boxHeight - scaledImageHeight) / 2

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
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
                        size = androidx.compose.ui.geometry.Size(
                            width = right - left,
                            height = bottom - top
                        ),
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
                )
            }
        }
    }
}