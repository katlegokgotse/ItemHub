package com.example.collectorapp.ui.screens.Items

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectorapp.ui.screens.Authentication.UserRegistration
import com.example.collectorapp.ui.screens.Categories.Categories
import com.example.collectorapp.ui.screens.Categories.CategoryList
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

class AddItemsViewModel: ViewModel() {
    private val _itemsState = MutableLiveData<ItemInformation>(ItemInformation())
    val itemsState: LiveData<ItemInformation> = _itemsState
    val _itemsList = mutableStateOf(ItemList())
    val itemDatabase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    fun updateItemName(itemName: String){
        _itemsState.value = _itemsState.value?.copy(itemName = itemName)
    }

    fun updateDescription(itemDescription: String) {
        _itemsState.value = itemsState.value?.copy(itemDescription = itemDescription)
    }

    fun updateYOA(itemYOA: String){
        _itemsState.value = _itemsState.value?.copy( yearOfAcquisition = itemYOA)
    }
    fun updateBrand(itemBrand: String){
        _itemsState.value = _itemsState.value?.copy(itemBrand = itemBrand)
    }
    fun saveItem(context: Context, itemInformation: ItemInformation){
        // User validation
        if (itemInformation.itemName.isBlank() ||
            itemInformation.itemDescription.isBlank() ||
            itemInformation.yearOfAcquisition.isBlank() ||
            itemInformation.itemBrand.isBlank()) {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            // Check for null and provide logging
            val itemsList = _itemsList.value
            if (itemsList != null) {
                val listItems = itemsList.itemList.toMutableList()
                listItems.add(itemInformation)
                _itemsList.value = itemsList.copy(itemList = listItems)
                Log.d("AddItemsViewModel", "Item list updated successfully with ${listItems.size} items.")

                // Add item to the database
              addDatabase(itemInformation)
                Toast.makeText(context, "Item: ${itemInformation.itemName} has been saved", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("AddItemsViewModel", "_itemsList.value is null")
                Toast.makeText(context, "Error: Items list is null", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("AddItemsViewModel", "Error adding item: ${e.message}", e)
            Toast.makeText(context, "Error adding item: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    fun fetchItems(){
        viewModelScope.launch {
            _itemsList.value = _itemsList.value.copy(itemList = _itemsList.value.itemList)
        }
    }
    fun createImageFile(context: Context): File {
        val itemInformation: ItemInformation = ItemInformation()
        val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", java.util.Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${itemInformation.itemName}_${itemInformation.itemCategory}_${timeStamp}"
        return File.createTempFile(imageFileName, ".jpg", context.externalCacheDir)

    }
    fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        )
    }
    fun handleCaptureImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            val permissionCheckResult = ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.CAMERA
            )
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                _itemsState.postValue(_itemsState.value?.copy(itemImage = uri))
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun addDatabase(item: ItemInformation) {
        try {
            val itemReference = itemDatabase.reference.child("users")
                .child("categories")
                .child("items")
                .push()

            val itemId = itemReference.key
            if (itemId != null) {
                itemReference.setValue(item.toMap())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("AddItemsViewModel", "Item saved successfully with ID: $itemId")
                        } else {
                            task.exception?.let {
                                Log.e("AddItemsViewModel", "Error saving item data", it)
                            }
                        }
                    }
            } else {
                Log.e("AddItemsViewModel", "Generated itemId is null")
            }
        } catch (e: Exception) {
            Log.e("AddItemsViewModel", "Exception in addDatabase: ${e.message}", e)
        }
    }
}

