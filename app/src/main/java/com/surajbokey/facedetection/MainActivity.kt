package com.surajbokey.facedetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.surajbokey.facedetection.detection.presentation.FaceDetectionScreen
import com.surajbokey.facedetection.gallery.presentation.GalleryScreen
import com.surajbokey.facedetection.permission.presentation.PermissionScreen
import com.surajbokey.facedetection.ui.theme.FaceDetectionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaceDetectionTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "gallery"
    ) {
        composable("permission") {
            PermissionScreen(onPermissionGranted = {
                navController.navigate("gallery") {
                    popUpTo("permission") { inclusive = true }
                }
            })
        }
        composable("gallery") {
            GalleryScreen(onImageSelected = { uri ->
                navController.navigate("faceDetection?imageUri=${uri}") {
                    launchSingleTop = true
                }
            })
        }
        composable(
            "faceDetection?imageUri={imageUri}",
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")
            FaceDetectionScreen(imageUri = imageUri)
        }
    }
}