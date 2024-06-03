package com.example.collectorapp.ui.screens.Search
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Categories.CategoriesHome
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import java.lang.reflect.Modifier

@Composable
fun Search(){
    val ac: AddCategoryViewModel = AddCategoryViewModel()
    val ai: AddItemsViewModel = AddItemsViewModel()
   CategoriesHome(addItemsViewModel = ai, addCategoryViewModel = ac)
}