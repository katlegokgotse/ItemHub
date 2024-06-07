package com.example.collectorapp.ui.screens.Categories

import java.util.Date

data class Categories(
    val categoryName: String = "",
    val categoryLocation: String = "",
    val categoryCreated: Date = Date()
)

data class CategoryList(
    val categoryList: List<Categories> = mutableListOf()
)