package com.example.collectorapp.ui.screens.Categories

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.collectorapp.ui.screens.Authentication.UserRegistration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Date

class AddCategoryViewModel: ViewModel() {
    val _categoryState = mutableStateOf(Categories())
    val _categoryListState = mutableStateOf(CategoryList())
    val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

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
        addCategoriesInDB(userId = auth.currentUser!!.uid, categoryName = categories.categoryName, categoryLocation = categories.categoryLocation, categoryCreated = categories.categoryCreated)
    }

    fun fetchCategories(){
              _categoryListState.value = _categoryListState.value.copy(categoryList = _categoryListState.value.categoryList)
    }
    private fun addCategoriesInDB(userId: String, categoryName: String, categoryLocation: String, categoryCreated: Date) {
        val userId = auth.currentUser?.uid ?: return

        val categoryAddition = Categories(categoryName = categoryName, categoryLocation = categoryLocation, categoryCreated = categoryCreated)
        database.reference.child("users")
            .child(userId)
            .child("Categories")
            .setValue(categoryAddition)
    }
}
