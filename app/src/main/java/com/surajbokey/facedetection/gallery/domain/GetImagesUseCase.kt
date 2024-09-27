package com.surajbokey.facedetection.gallery.domain

import com.surajbokey.facedetection.core.models.ImageWithFaces
import com.surajbokey.facedetection.detection.FaceDetectorHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: GalleryRepository,
    private val helper: FaceDetectorHelper
) {
    operator fun invoke(): Flow<ImageWithFaces> = flow {

        val concurrencyLevel = Runtime.getRuntime().availableProcessors()
        val semaphore = Semaphore(concurrencyLevel)

        repository.getGalleryImages().asFlow().map { image ->
            withContext(Dispatchers.Default) {
                async {
                    semaphore.acquire()
                    try {
                        val bitmap = repository.getBitmap(image.uri)
                        val faces = helper.detectImage(bitmap)?.detections()
                        if (faces?.isNotEmpty() == true) {
                            ImageWithFaces(image.uri, faces.map { it.boundingBox() })
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        null
                    } finally {
                        semaphore.release()
                    }
                }
            }
        }
            .buffer(concurrencyLevel)
            .collect { deferred ->
                val result = deferred.await()
                if (result != null) {
                    emit(result)
                }
            }
    }
}