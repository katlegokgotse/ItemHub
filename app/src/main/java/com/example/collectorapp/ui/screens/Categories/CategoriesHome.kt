package com.example.collectorapp.ui.screens.Categories

import CameraScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R
import com.example.collectorapp.ui.composables.HeadingText
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel


@Composable
fun CategoriesHome(
    addItemsViewModel: AddItemsViewModel,
    addCategoryViewModel: AddCategoryViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier.height(250.dp)
            ){
                Image(painter = painterResource(id = R.drawable.ob2),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth)
                HeadingText(value = addCategoryViewModel._categoryState.value.categoryName)
            }
                    CameraScreen()
            }
        }
    }