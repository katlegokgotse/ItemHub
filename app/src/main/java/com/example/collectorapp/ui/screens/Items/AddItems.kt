package com.example.collectorapp.ui.screens.Items

import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel

@Composable
fun AddingItems(
    addItemsViewModel: AddItemsViewModel,
    addCategoryViewModel: AddCategoryViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val file = addItemsViewModel.createImageFile(context)
    val photoUri = addItemsViewModel.getUriForFile(context, file)
    val itemInformation by addItemsViewModel.itemsState.observeAsState(ItemInformation())

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            addItemsViewModel.handleCaptureImage(context, photoUri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(photoUri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier
        .padding(40.dp)
        .fillMaxSize()) {
        LazyColumn {
            item {
                CaptureImageSection(
                    addItemsViewModel = addItemsViewModel,
                    addCategoryViewModel = addCategoryViewModel,
                    photoUri = photoUri,
                    permissionLauncher = permissionLauncher,
                    cameraLauncher = cameraLauncher,
                )
            }
            item {
                UserItemsInput(addItemsViewModel = addItemsViewModel)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    addItemsViewModel.saveItem(context, itemInformation)
                        navController.navigate("home")
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}
@Composable
fun CaptureImageSection(
    addItemsViewModel: AddItemsViewModel,
    addCategoryViewModel: AddCategoryViewModel,
    photoUri: Uri,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>
) {
    val itemInformation by addItemsViewModel.itemsState.observeAsState(ItemInformation())

    Column(modifier = Modifier.fillMaxWidth()) {
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(shape = MaterialTheme.shapes.small),
            onClick = {
            val permissionCheckResult = ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.CAMERA
            )
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(photoUri)
            } else {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }) {
            Image(painter = painterResource(id = R.drawable.camera),
                contentDescription = "camera")
        }
        itemInformation.itemImage?.let {
            MyItemsCard(
                addItemsViewModel = addCategoryViewModel,
                imageUri = it ,
                contentDescription = itemInformation.itemDescription ?: "",
                title = itemInformation.itemName ?: "",
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun UserItemsInput(addItemsViewModel: AddItemsViewModel) {
    val itemInformation by addItemsViewModel.itemsState.observeAsState(ItemInformation())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = itemInformation.itemName,
            onValueChange = { addItemsViewModel.updateItemName(it) },
            label = { Text(text = "Enter Item Name") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = itemInformation.itemBrand,
            onValueChange = { addItemsViewModel.updateBrand(it) },
            label = { Text(text = "Enter Item Brand") }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            value = itemInformation.itemDescription,
            onValueChange = { addItemsViewModel.updateDescription(it) },
            label = { Text(text = "Enter Item Description") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = itemInformation.yearOfAcquisition,
            onValueChange = { addItemsViewModel.updateYOA(it) },
            label = { Text(text = "Enter Year Of Acquisition") }
        )
    }
}

