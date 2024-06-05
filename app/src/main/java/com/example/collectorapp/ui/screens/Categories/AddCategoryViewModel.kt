package com.example.collectorapp.ui.screens.Categories

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AddCategoryViewModel : ViewModel() {
    val _categoryState = MutableStateFlow(Categories())
    val categoryState: StateFlow<Categories> = _categoryState

    val _categoryListState = MutableStateFlow(CategoryList())
    val categoryListState: StateFlow<CategoryList> = _categoryListState

    fun updateCategoryName(categoryName: String) {
        _categoryState.update { it.copy(categoryName = categoryName) }
    }

    fun updateCategoryLocation(categoryLocation: String) {
        _categoryState.update { it.copy(categoryLocation = categoryLocation) }
    }

    fun updateCategoryCreation(creationDate: String) {
        _categoryState.update { it.copy(categoryCreated = creationDate) }
    }

    fun saveCategory(category: Categories) {
        _categoryListState.update {
            it.copy(categoryList = it.categoryList + category)
        }
    }

    fun fetchCategories() {
        // In a real scenario, this could involve fetching data from a database or repository
        _categoryListState.update { it.copy(categoryList = it.categoryList) }
    }
}

data class Categories(
    val categoryName: String = "",
    val categoryLocation: String = "",
    val categoryCreated: String = ""
)

data class CategoryList(
    val categoryList: List<Categories> = emptyList()
)
