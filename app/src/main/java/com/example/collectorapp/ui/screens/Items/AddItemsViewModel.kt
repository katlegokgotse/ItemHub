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
        _itemsState.value = _itemsState.value!!.copy(itemName = itemName)
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
        if (itemInformation.itemName.isBlank()) {
            Toast.makeText(context, "Item name is required", Toast.LENGTH_SHORT).show()
            return
        }

        if (itemInformation.itemDescription.isBlank()) {
            Toast.makeText(context, "Item description is required", Toast.LENGTH_SHORT).show()
            return
        }

        if (itemInformation.yearOfAcquisition.isBlank()) {
            Toast.makeText(context, "Year of acquisition is required", Toast.LENGTH_SHORT).show()
            return
        }

        if (itemInformation.itemBrand.isBlank()) {
            Toast.makeText(context, "Item brand is required", Toast.LENGTH_SHORT).show()
            return
        }
        val listItems = _itemsList.value.itemList.toMutableList()
        listItems.add(itemInformation)
        _itemsList.value = _itemsList.value.copy(itemList = listItems)
        addDatabase(itemInformation)
    }
    fun fetchItems(){
        viewModelScope.launch {
            _itemsList.value = _itemsList.value.copy(itemList = _itemsList.value.itemList)
        }
    }
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", java.util.Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
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
    private fun addDatabase(
        item: ItemInformation
    ){
        ItemInformation()
        val itemId = itemDatabase.reference.child("users").child("items").push().key
        if (itemId != null) {
            itemDatabase.reference.child("users").child("items").setValue(item)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // The operation was successful
                        Log.d(ContentValues.TAG, "Items saved successfully")
                    } else {
                        // The operation failed
                        task.exception?.let {
                            Log.e(ContentValues.TAG, "Error saving user data", it)
                        }
                    }
                }
        }
    }

}

