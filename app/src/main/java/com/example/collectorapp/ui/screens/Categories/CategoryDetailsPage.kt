package com.example.collectorapp.ui.screens.Categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel

@Composable
fun CategoryDetailPage(ac: AddCategoryViewModel, ai: AddItemsViewModel, navController: NavController) {
    LazyColumn {
        item{
            Box{
               CategoryDetailPage(ac, ai, navController)
            }
        }
    }
}