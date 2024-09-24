package com.surajbokey.facedetection.tagging.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FaceTagDialog(
    faceId: String,
    initialName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    name = initialName

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tag Face") },
        text = {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Enter name")
                }
            )
        },
        confirmButton = {
            Button(onClick = {
                onSave(name)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}