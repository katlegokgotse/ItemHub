package com.example.collectorapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Authentication.SignIn.LoginInterface
import com.example.collectorapp.ui.screens.Authentication.Signup.RegisterInterface
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Categories.CategoriesHome
import com.example.collectorapp.ui.screens.Categories.UserCategoryInput
import com.example.collectorapp.ui.screens.Items.AddItems.AddingItems
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.screens.Startup.CTA.CallToAction
import com.example.collectorapp.ui.screens.Startup.Onboarding.OnBoarding
import com.example.collectorapp.ui.screens.Startup.SplashScreen.SplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigateAuth(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "splash_screen",
    signInViewModel: AuthenticationViewModel,
    addCategoryViewModel: AddCategoryViewModel,
    addItemsViewModel: AddItemsViewModel
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable("login_interface"){
            LoginInterface( navController = navController)
        }
        composable("registration_interface"){
            RegisterInterface(authenticationViewModel = signInViewModel, navController = navController)
        }
        composable("home"){
            MyBottomAppBar(signInViewModel = signInViewModel)
        }
        composable(route = "create_category"){
            UserCategoryInput(addCategoryViewModel, navController = navController)
        }
        composable(route = "create_items"){
            AddingItems(addItemsViewModel = addItemsViewModel, addCategoryViewModel = addCategoryViewModel)
        }
        composable(route = "splash_screen"){
            SplashScreen(navController = navController)
        }

        composable(route = "onboarding_page"){
            OnBoarding(navController = navController)
        }
        composable(route = "call_to_action"){
            CallToAction(navController = navController)
        }

        composable(route = "categories_home"){
           CategoriesHome(addItemsViewModel = addItemsViewModel,
               addCategoryViewModel = addCategoryViewModel)
        }
    }

}