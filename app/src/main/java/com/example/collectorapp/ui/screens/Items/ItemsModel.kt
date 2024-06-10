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
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "itemName" to itemName,
            "itemBrand" to itemBrand,
            "itemDescription" to itemDescription,
            "yearOfAcquisition" to yearOfAcquisition,
            "itemCategory" to itemCategory,
            "itemImage" to itemImage?.toString() // Convert Uri to String
        )
    }
}
data class ItemList(
    val itemList: List<ItemInformation> = mutableListOf()
)