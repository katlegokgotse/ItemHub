package com.example.collectorapp.ui.screens.Items

import android.net.Uri
import com.example.collectorapp.ui.screens.Categories.CategoryList

data class ItemInformation(
    val itemCategory: List<CategoryList> = mutableListOf(),
    val itemName: String = "",
    val itemDescription: String = "",
    val itemBrand: String =  "",
    val yearOfAcquisition: String = "",
    val itemImage: Uri? = null
)
data class ItemList(
    val itemList: List<ItemInformation> = mutableListOf()
)