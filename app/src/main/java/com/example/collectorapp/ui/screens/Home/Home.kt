package com.example.collectorapp.ui.screens.Home
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collectorapp.AddCategorySection
import com.example.collectorapp.CategorySection
import com.example.collectorapp.R
import com.example.collectorapp.ui.composables.HeadingText
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Categories.CreateCategory
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel

@Composable
fun Home(s: AuthenticationViewModel,
         addItemsViewModel: AddItemsViewModel,
         addCategoryViewModel: AddCategoryViewModel,
         navController: NavController) {
    val userName by s.userName.observeAsState("Loading")

    LaunchedEffect(Unit) {
        s.displayUserName()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SearchBar(addItemsViewModel = addItemsViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Welcome $userName")
                TextButton(onClick = {
                    navController.navigate("categories_home")
                }) {
                    HeadingText(value = "Categories")
                }
                LazyRow {
                    item{
                        AddCategorySection(
                            a = addCategoryViewModel,
                            navController = navController,
                            onClick = {
                                navController.navigate("create_category")
                            }
                        )
                    }
                    items(
                        addCategoryViewModel._categoryListState.value.categoryList.ifEmpty {
                            listOf(null)
                        }
                    ) { category ->
                        if (category != null) {
                            CategorySection(a = addCategoryViewModel, navController = navController)
                        } else {
                            AddCategorySection(
                                a = addCategoryViewModel,
                                navController = navController,
                                onClick = {
                                    navController.navigate("create_category")
                                }
                            )
                        }
                    }
                }
            }
            item {
                HeadingText(value = "My Collection")
                LazyRow {
                    items(
                        addCategoryViewModel._categoryListState.value.categoryList.ifEmpty {
                            listOf(null)
                        }
                    ) { category ->
                        if (category != null) {
                            CategorySection(a = addCategoryViewModel, navController)
                        } else {
                            CreateCategory(
                                painter = painterResource(id = R.drawable.cto),
                                contentDescription = addCategoryViewModel._categoryState.value.categoryName,
                                title = addCategoryViewModel._categoryState.value.categoryName,
                                modifier = Modifier.width(150.dp),
                                onClick = { navController.navigate("create_category") }
                            )
                        }

                    }

                }
            }
        }
    }
}
@Composable
fun SearchBar(addItemsViewModel: AddItemsViewModel){
    Column {
        OutlinedTextField(value = addItemsViewModel.itemsState.value!!.itemName,
            onValueChange = {addItemsViewModel.updateItemName(it) },
            label = { Text("Search for Item")}
        )
    }
}
