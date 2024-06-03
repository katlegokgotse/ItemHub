package com.example.collectorapp.ui.screens.Categories

import CameraScreen
import MyItems
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R
import com.example.collectorapp.ui.composables.HeadingText
import com.example.collectorapp.ui.screens.Authentication.Signup.authenticationViewModel
import com.example.collectorapp.ui.screens.Categories.ui.theme.CollectorAppTheme
import com.example.collectorapp.ui.screens.Items.AddItems.AddingItems
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