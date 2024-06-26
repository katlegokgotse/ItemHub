package com.example.collectorapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Authentication.LoginInterface
import com.example.collectorapp.ui.screens.Authentication.RegisterInterface
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Categories.CategoriesHome
import com.example.collectorapp.ui.screens.Categories.CategoryDetailPage
import com.example.collectorapp.ui.screens.Categories.UserCategoryInput
import com.example.collectorapp.ui.screens.Goals.EnumGoals
import com.example.collectorapp.ui.screens.Goals.GoalsDetailsPage
import com.example.collectorapp.ui.screens.Items.AddingItems
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
    startDestination: String = "create_items",
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
            LoginInterface(viewModel = signInViewModel, navController = navController)
        }
        composable("registration_interface"){
            RegisterInterface(authenticationViewModel = signInViewModel, navController = navController)
        }
        composable("home"){
            MyBottomAppBar(signInViewModel = signInViewModel)
        }
        composable(route = "create_category"){
            UserCategoryInput(addCategoryViewModel = addCategoryViewModel, navController = navController)
        }
        composable(route = "create_items"){
            AddingItems(
                addItemsViewModel = addItemsViewModel,
                addCategoryViewModel = addCategoryViewModel,
                navController = navController)
        }
        composable(route = "splash_screen"){
            SplashScreen(navController = navController)
        }

        composable(route = "onboarding_page"){
            OnBoarding(navController = navController)
        }

        composable(route = "categoryDetailsPage"){
            CategoryDetailPage(ai = addItemsViewModel, ac = addCategoryViewModel, navController = navController)
        }

        composable(route = "categories_home"){
            CategoriesHome(addItemsViewModel = addItemsViewModel,
                addCategoryViewModel = addCategoryViewModel, navController = navController)
        }
        composable(route = "GoalsDetailsPage"){
            GoalsDetailsPage( selectedGoal = EnumGoals.Starter, addItemsViewModel = addItemsViewModel)
        }
    }

}
