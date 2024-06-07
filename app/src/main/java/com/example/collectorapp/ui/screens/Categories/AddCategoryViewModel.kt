package com.example.collectorapp.ui.screens.Categories

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectorapp.ui.screens.Items.ItemInformation
import kotlinx.coroutines.launch
import java.util.Date

class AddCategoryViewModel: ViewModel() {
    val _categoryState = mutableStateOf(Categories())
    val _categoryListState = mutableStateOf(CategoryList())

    fun updateCategoryName(_categoryName: String){
        _categoryState.value = _categoryState.value.copy(categoryName = _categoryName)
    }

    fun updateCategoryLocation(_categoryLocation: String) {
        _categoryState.value = _categoryState.value.copy(categoryLocation = _categoryLocation)
    }
    fun updateCategoryCreation() {
        _categoryState.value = _categoryState.value.copy(categoryCreated = Date())
    }

    fun saveCategory(categories: Categories){
        val category = _categoryListState.value.categoryList.toMutableList()
        category.add(categories)
        _categoryListState.value = _categoryListState.value.copy(categoryList = category)
    }

    fun fetchCategories(){
              _categoryListState.value = _categoryListState.value.copy(categoryList = _categoryListState.value.categoryList)
    }
}
data class Categories(
    val categoryName: String = "",
    val categoryLocation: String = "",
    val categoryCreated: Date = Date()
)

data class CategoryList(
    val categoryList: List<Categories> = mutableListOf()
)