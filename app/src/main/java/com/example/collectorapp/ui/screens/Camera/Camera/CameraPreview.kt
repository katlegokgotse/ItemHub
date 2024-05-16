package com.example.collectorapp.ui.screens.Camera.Camera

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
){
    val lifcycleOwner = LocalLifecycleOwner.current
    AndroidView(factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifcycleOwner)
        }
    },
        modifier = Modifier)
}