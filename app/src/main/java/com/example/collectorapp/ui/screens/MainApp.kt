package com.example.collectorapp
import Notification
import Profile
import Screens
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Authentication.UserList
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Categories.CategoryList
import com.example.collectorapp.ui.screens.Categories.CreateCategory
import com.example.collectorapp.ui.screens.Home.Home
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.screens.Items.MyCategoryCard
import com.example.collectorapp.ui.screens.Items.MyItemsCard
import com.example.collectorapp.ui.screens.Search.Search
import com.example.collectorapp.ui.theme.CollectorAppTheme
class Main : ComponentActivity() {
    val signInViewModel: AuthenticationViewModel = AuthenticationViewModel()
    val addItemsViewModel: AddItemsViewModel = AddItemsViewModel()
    val addCategoryViewModel: AddCategoryViewModel = AddCategoryViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollectorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        SearchBar(addItemsViewModel = addItemsViewModel)
                        MyBottomAppBar(signInViewModel, )
                        CategorySection(addCategoryViewModel, navController = rememberNavController())
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySection(a: AddCategoryViewModel, navController: NavController) {
    Column {
        Row {
            MyCategoryCard(a,
                painter = painterResource(id = R.drawable.cto),
                contentDescription = a._categoryState.value.categoryName,
                title = a._categoryListState.value.categoryList[0].categoryName,
                modifier = Modifier.width(150.dp),
                onClick = {navController.navigate("categories_home")})
        }
    }
}

@Composable
fun AddCategorySection(a: AddCategoryViewModel, navController: NavController, onClick: () -> Unit) {
    CreateCategory(
                painter = painterResource(id = R.drawable.cto),
                contentDescription = a._categoryState.value.categoryName,
                title = a._categoryState.value.categoryName,
                modifier = Modifier.width(150.dp),
                onClick = { onClick }
            )
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
@Composable
fun MyBottomAppBar(signInViewModel: AuthenticationViewModel) {
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent
            ) {
                //Home
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Home
                        navigationController.navigate(Screens.Home.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Home) Color.White else Color.Gray
                    )
                }
                //Notification
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Notifications
                        navigationController.navigate(Screens.Notification.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Notifications) Color.White else Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ){
                    FloatingActionButton(onClick = {
                        navigationController.navigate("create_category")
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.Red)
                    }
                }
                //Profile
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.AccountCircle
                        navigationController.navigate(Screens.Profile.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.AccountCircle) Color.White else Color.Gray
                    )
                }
                //Search
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Search
                        navigationController.navigate(Screens.Search.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Search) Color.White else Color.Gray
                    )
                }
                //Create a new Category
            }
        }
    ) {paddingValues ->
        NavHost(navController = navigationController,
            startDestination = Screens.Home.screen,
            modifier = Modifier.padding(paddingValues)){
            val a: AddItemsViewModel = AddItemsViewModel()
            val ac: AddCategoryViewModel = AddCategoryViewModel()
            composable(Screens.Home.screen){
                Home(s = signInViewModel, addItemsViewModel = a, addCategoryViewModel = ac, navController = navigationController)
            }
            composable(Screens.Search.screen){
                Search()
            }
            composable(Screens.Notification.screen){
                Notification(addCategoryViewModel = ac, navController = navigationController)
            }
            composable(Screens.Profile.screen){
               Profile(signInViewModel, ac)
            }
        }
    }
}
