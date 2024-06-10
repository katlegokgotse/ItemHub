package com.example.collectorapp.ui.screens.Categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel

@Composable
fun CategoryDetailsPage(ac: AddCategoryViewModel, ai: AddItemsViewModel) {
    LazyColumn {
        item{
            Box{
                CategoriesHome(addItemsViewModel = ai, addCategoryViewModel = ac)
            }
        }
    }
}