package com.example.collectorapp.ui.screens.Categories

import androidx.lifecycle.ViewModel

class CategoriesViewModel: ViewModel() {
}

data class ItemCategory(
    val categoryName: String = "",
    val categoryLocation: String = ""
)