package com.example.collectorapp.ui.screens.Goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun GoalsComposable(){
    Column(){

    }
}

@Composable
fun LinearGoalProgressIndicator(addItemsViewModel: AddItemsViewModel){
    var currentGoalProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(addItemsViewModel._itemsList){
        currentGoalProgress = addItemsViewModel._itemsList.value.itemList.size.toFloat()
    }
    Column {
        LinearProgressIndicator(
            progress = currentGoalProgress / 10,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CircularGoalProgressIndicator(addItemsViewModel: AddItemsViewModel, goalProgress: Float){
    var currentGoalProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(addItemsViewModel._itemsList){
        currentGoalProgress = addItemsViewModel._itemsList.value.itemList.size.toFloat()
    }
    Column {
        CircularProgressIndicator(
            progress = goalProgress,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun GoalsDetailsPage(selectedGoal: EnumGoals, addItemsViewModel: AddItemsViewModel){
    val goalTitle = selectedGoal.goalDetails.toString()
    val goalDescription = selectedGoal.goalDetails.goalDescription
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(25.dp)
    ){
        Column{
            Text(text = goalTitle, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = goalDescription)
            LinearGoalProgressIndicator(addItemsViewModel)
        }

    }
}
@Composable
fun GoalsSummaryPage(selectedGoal: EnumGoals, addItemsViewModel: AddItemsViewModel) {
    val goalDetails = selectedGoal.goalDetails
    val goalTitle = goalDetails.toString()
    val goalDescription = goalDetails.goalDescription
    val goalProgress = when (goalDetails) {
        is GoalDetails.Starter -> addItemsViewModel._itemsList.value.itemList.size.toFloat() / 1
        is GoalDetails.Collector -> addItemsViewModel._itemsList.value.itemList.size.toFloat() / 3
        is GoalDetails.Packrat -> addItemsViewModel._itemsList.value.itemList.size.toFloat() / 10
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(25.dp)
    ) {
        Column {
            Text(text = goalTitle, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = goalDescription)
            CircularGoalProgressIndicator(addItemsViewModel, goalProgress)
        }
    }
}



@Preview
@Composable
fun GoalsPreview(){
    GoalsComposable()
}