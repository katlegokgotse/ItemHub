package com.example.collectorapp.ui.screens.Items

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectorapp.ui.screens.Categories.ItemCategory
import kotlinx.coroutines.launch

class AddItemsViewModel: ViewModel() {
    val _itemsState = mutableStateOf(ItemInformation())
    val _itemsList = mutableStateOf(ItemList())
    fun updateItemName(itemName: String){
        _itemsState.value = _itemsState.value.copy(itemName = itemName)
    }

    fun updateDescription(itemDescription: String) {
        _itemsState.value = _itemsState.value.copy(itemDescription = itemDescription)
    }

    fun updateYOA(itemYOA: String){
        _itemsState.value = _itemsState.value.copy( yearOfAcquisition = itemYOA)
    }
    fun updateBrand(itemBrand: String){
        _itemsState.value = _itemsState.value.copy(itemBrand = itemBrand)
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
}

data class ItemInformation(
    val itemCategory: List<ItemCategory> = mutableListOf(),
    val itemName: String = "",
    val itemDescription: String = "",
    val itemBrand: String =  "",
    val yearOfAcquisition: String = "",
)
data class ItemList(
    val itemList: List<ItemInformation> = mutableListOf()
)