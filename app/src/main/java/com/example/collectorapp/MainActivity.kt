package com.example.collectorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.theme.CollectorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollectorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val signInViewModel: AuthenticationViewModel = AuthenticationViewModel()
                    val addCategoryViewModel: AddCategoryViewModel = AddCategoryViewModel()
                    val addItemsViewModel: AddItemsViewModel = AddItemsViewModel()
                    NavigateAuth(navController = rememberNavController(),
                        signInViewModel = signInViewModel,
                        addCategoryViewModel = addCategoryViewModel,
                        addItemsViewModel = addItemsViewModel)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CollectorAppTheme {

    }
}