package com.example.collectorapp.ui.screens.Items

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectorapp.ui.screens.Categories.Categories
import com.example.collectorapp.ui.screens.Categories.CategoryList
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

class AddItemsViewModel: ViewModel() {
    private val _itemsState = MutableLiveData<ItemInformation>()
    val itemsState: LiveData<ItemInformation> = _itemsState
    val _itemsList = mutableStateOf(ItemList())
    val itemsList: ItemList get() = _itemsList.value

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
    fun saveItem(itemInformation: ItemInformation){
        val listItems = _itemsList.value.itemList.toMutableList()
        listItems.add(itemInformation)
        _itemsList.value = _itemsList.value.copy(itemList = listItems)
    }
    fun fetchItems(){
        viewModelScope.launch {
            _itemsList.value = _itemsList.value.copy(itemList = _itemsList.value.itemList)
        }
    }
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        return File.createTempFile(imageFileName, ".jpg", context.externalCacheDir)

    }
    fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            context.packageName + ".provider", file
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

}

