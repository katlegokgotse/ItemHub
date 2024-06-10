package com.example.collectorapp.ui.screens.Categories

import CameraScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.collectorapp.R
import com.example.collectorapp.ui.composables.HeadingText
import com.example.collectorapp.ui.screens.Goals.EnumGoals
import com.example.collectorapp.ui.screens.Goals.GoalsDetailsPage
import com.example.collectorapp.ui.screens.Goals.GoalsSummaryPage
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesHome(
    addItemsViewModel: AddItemsViewModel,
    addCategoryViewModel: AddCategoryViewModel,
    navController: NavController
) {

    val categories = addCategoryViewModel.fetchCategories()
    Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
        Box(modifier = Modifier.height(250.dp)){
                    Image(painter = painterResource(id = R.drawable.ob2),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 100f,
                            endY = 550f
                        )
                    )
            )
                Text(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(25.dp),
                    fontSize = 48.sp,
                    text = categories.toString())
                }
        Column(){
            Text("Goal", modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), textAlign = TextAlign.Start, fontSize = 26.sp)
            Card(modifier = Modifier.width(200.dp)
                .padding(10.dp)
                ,onClick = {
                    navController.navigate("GoalsDetailsPage")
                }) {
                GoalsSummaryPage(selectedGoal = EnumGoals.Packrat, addItemsViewModel = addItemsViewModel)
            }

        }
        }
}