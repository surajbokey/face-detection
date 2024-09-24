/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.surajbokey.facedetection.tagging

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FaceDetectorHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    // For this example this needs to be a var so it can be reset on changes. If the faceDetector
    // will not change, a lazy val would be preferable.
    private var faceDetector: FaceDetector? = null

    init {
        setupFaceDetector()
    }

    fun clearFaceDetector() {
        faceDetector?.close()
        faceDetector = null
    }

    // Initialize the face detector using current settings on the
    // thread that is using it. CPU can be used with detectors
    // that are created on the main thread and used on a background thread, but
    // the GPU delegate needs to be used on the thread that initialized the detector
    private fun setupFaceDetector() {
        // Set general detection options, including number of used threads
        val baseOptionsBuilder = BaseOptions.builder()

        // Use the specified hardware for running the model. Default to CPU
        when (currentDelegate) {
            DELEGATE_CPU -> {
                baseOptionsBuilder.setDelegate(Delegate.CPU)
            }

            DELEGATE_GPU -> {
                // Is there a check for GPU being supported?
                baseOptionsBuilder.setDelegate(Delegate.GPU)
            }
        }

        val modelName = "blaze_face_short_range.tflite"

        baseOptionsBuilder.setModelAssetPath(modelName)

        try {
            val optionsBuilder =
                FaceDetector.FaceDetectorOptions.builder()
                    .setBaseOptions(baseOptionsBuilder.build())
                    .setMinDetectionConfidence(threshold)
                    .setRunningMode(RunningMode.IMAGE)

            optionsBuilder.setRunningMode(RunningMode.IMAGE)

            val options = optionsBuilder.build()
            faceDetector = FaceDetector.createFromOptions(context, options)
        } catch (e: IllegalStateException) {
            Log.e(TAG, "TFLite failed to load model with error: " + e.message)
        } catch (e: RuntimeException) {
            Log.e(TAG, "Face detector failed to load model with error: " + e.message)
        }
    }

    // Return running status of recognizer helper
    fun isClosed(): Boolean {
        return faceDetector == null
    }

    // Accepted a Bitmap and runs face detection inference on it to return results back
    // to the caller
    fun detectImage(image: Bitmap): FaceDetectorResult? {

        if (faceDetector == null) return null

        // Inference time is the difference between the system time at the start and finish of the
        // process
        val startTime = SystemClock.uptimeMillis()

        // Convert the input Bitmap face to an MPImage face to run inference
        val mpImage = BitmapImageBuilder(image).build()

        // Run face detection using MediaPipe Face Detector API
        faceDetector?.detect(mpImage)?.also { detectionResult ->
            val inferenceTimeMs = SystemClock.uptimeMillis() - startTime
            return detectionResult
        }

        // If faceDetector?.detect() returns null, this is likely an error. Returning null
        // to indicate this.
        return null
    }

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1
        const val THRESHOLD_DEFAULT = 0.5F
        val threshold: Float = THRESHOLD_DEFAULT
        val currentDelegate: Int = DELEGATE_CPU
        const val TAG = "FaceDetectorHelper"
    }
}

data class ResultBundle(
    val results: List<FaceDetectorResult>,
    val inferenceTime: Long,
    val inputImageHeight: Int,
    val inputImageWidth: Int,
)