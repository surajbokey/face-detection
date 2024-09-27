package com.surajbokey.facedetection

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
        startDestination = Screen.Permission.route
    ) {
        composable(Screen.Permission.route) {
            PermissionScreen(
                onPermissionGranted = {
                    navController.navigate(Screen.Gallery.route) {
                        popUpTo(Screen.Permission.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Gallery.route) {
            GalleryScreen(
                onImageSelected = { uri ->
                    val encodedUri = Uri.encode(uri.toString())
                    navController.navigate("${Screen.FaceDetection.route}?${Arguments.IMAGE_URI}=$encodedUri") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${Screen.FaceDetection.route}?${Arguments.IMAGE_URI}={${Arguments.IMAGE_URI}}",
            arguments = listOf(
                navArgument(Arguments.IMAGE_URI) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val imageUriString = backStackEntry.arguments?.getString(Arguments.IMAGE_URI)
            val imageUri = imageUriString?.let { Uri.parse(it) }
            FaceDetectionScreen(imageUri = imageUriString)
        }
    }
}

sealed class Screen(val route: String) {
    data object Permission : Screen("permission")
    data object Gallery : Screen("gallery")
    data object FaceDetection : Screen("faceDetection")
}

object Arguments {
    const val IMAGE_URI = "imageUri"
}