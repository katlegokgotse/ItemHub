package com.example.collectorapp.ui.screens.Categories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Authentication.UserRegistration
import com.example.collectorapp.ui.screens.Categories.ui.theme.CollectorAppTheme

@Composable
fun AddNewCategories(addCategoryViewModel: AddCategoryViewModel, navController: NavController){
    Box(modifier = Modifier.padding(40.dp)){
        Column {
            AddMyCategoryCard(
                addCategoryViewModel =addCategoryViewModel ,
                painter = painterResource(id = R.drawable.cto),
                contentDescription = addCategoryViewModel._categoryState.value.categoryName,
                title = addCategoryViewModel._categoryState.value.categoryName,
                modifier = Modifier,
                onClick = {
                   navController.navigate("home")
                })

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCategoryInput(addCategoryViewModel: AddCategoryViewModel, navController: NavController){
    Column(){
        Row(){

            // Optional actions for the app bar
            Button(onClick = {
                val newCategory = Categories(
                    addCategoryViewModel._categoryState.value.categoryName,
                    addCategoryViewModel._categoryState.value.categoryLocation,
                    addCategoryViewModel._categoryState.value.categoryCreated
                )
                addCategoryViewModel.saveCategory(newCategory)
                navController.navigate("home")
            }
            ) {
                Text(text = "Save")
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
        ){
            Column(
                modifier = Modifier.padding(40.dp)
            ){
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = addCategoryViewModel._categoryState.value.categoryName,
                    onValueChange = {addCategoryViewModel.updateCategoryName(it)},
                    label = { Text(text = "Enter Category Name")}
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = addCategoryViewModel._categoryState.value.categoryLocation,
                    onValueChange = {addCategoryViewModel.updateCategoryLocation(it)},
                    label = { Text(text = "Enter Category Location")}
                )
            }

        }
    }
}

@Composable
fun AddMyCategoryCard(
    addCategoryViewModel: AddCategoryViewModel,
    painter: Painter, contentDescription: String,
    title: String, modifier: Modifier,
    onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ) {
        Column {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                //elevation = 5.dp
            ) {
                Box(
                    modifier = Modifier.height(200.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = contentDescription,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        IconButton(
                            modifier = Modifier.background(Color.White),
                            onClick = onClick
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
            }
            Text(text = addCategoryViewModel._categoryState.value.categoryName)
        }
    }
}
