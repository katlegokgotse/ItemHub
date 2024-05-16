package com.example.collectorapp.ui.screens.Categories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Authentication.UserRegistration
import com.example.collectorapp.ui.screens.Categories.ui.theme.CollectorAppTheme
import com.example.collectorapp.ui.screens.Items.MyItems.AddMyCategoryCard

class CreateCategories : ComponentActivity() {
    private val addCategoryViewModel = AddCategoryViewModel() // Initialize your ViewModel
  //  private val navController  = rememberNavController() // Initialize your NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollectorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun AddNewCategories(addCategoryViewModel: AddCategoryViewModel, navController: NavController){
    Box(modifier = Modifier.padding(40.dp)){
        Column(){
            AddMyCategoryCard(
                addCategoryViewModel = addCategoryViewModel,
                painter = painterResource(id = R.drawable.cto),
                contentDescription = addCategoryViewModel._categoryState.value.categoryName,
                title = addCategoryViewModel._categoryState.value.categoryName,
                modifier = Modifier,
                onClick = {
                    navController.navigate(route = "create_category")
                })

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCategoryInput(addCategoryViewModel: AddCategoryViewModel, navController: NavController){
    Scaffold(
        modifier = Modifier.height(35.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "My App") },

                actions = {
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
            )
        }
    ){
        padding ->
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = addCategoryViewModel._categoryState.value.categoryCreated,
                        onValueChange = {addCategoryViewModel.updateCategoryCreation(it)},
                        label = { Text(text = "Enter Category Release")}
                    )
                }
            }

        }
    }


}



@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    //AddNewCategories()
}