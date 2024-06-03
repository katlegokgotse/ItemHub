package com.example.collectorapp.ui.screens.Home
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.screens.Items.ItemInformation
import com.example.collectorapp.ui.screens.Items.MyItems.MyItemsCard

@Composable
fun Home(s: AuthenticationViewModel,
         addItemsViewModel: AddItemsViewModel,
         addCategoryViewModel: AddCategoryViewModel,
         navController: NavController) {
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
                Text(text = "Welcome ${s._userReg.value.firstName} ${s._userReg.value.lastName}")
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
                            CategorySection(a = addCategoryViewModel)
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
                            CategorySection(a = addCategoryViewModel)
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
        }
    }
}
@Composable
fun SearchBar(addItemsViewModel: AddItemsViewModel){
    Column {
        OutlinedTextField(value = addItemsViewModel._itemsState.value.itemName,
            onValueChange = {addItemsViewModel.updateItemName(it) },
            label = { Text("Search for Item")}
        )
    }
}
