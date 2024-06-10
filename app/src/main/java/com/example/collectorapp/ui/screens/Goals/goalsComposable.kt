package com.example.collectorapp.ui.screens.Goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectorapp.ui.screens.Goals.EnumGoals
import com.example.collectorapp.ui.screens.Goals.GoalDetails

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
fun CircularGoalProgressIndicator(addItemsViewModel: AddItemsViewModel){
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
fun GoalsDetailsPage(selectedGoal: EnumGoals, addItemsViewModel: AddItemsViewModel){
    val goalTitle = selectedGoal.goalDetails.toString()
    val goalDescription = selectedGoal.goalDetails.goalDescription
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ){
        Column{
            Text(text = goalTitle)
            Text(text = goalDescription)
            LinearGoalProgressIndicator(addItemsViewModel)
        }

    }
}
@Composable
fun GoalSummaryPage(selectedGoal: EnumGoals, addItemsViewModel: AddItemsViewModel){
    val goalTitle = selectedGoal.goalDetails.toString()
    val goalDescription = selectedGoal.goalDetails.goalDescription
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ){
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Blue)
            ){
                Column{
                    Text(text = goalTitle)
                    Text(text = goalDescription)
                    LinearGoalProgressIndicator(addItemsViewModel)
                }

            }
        }
    }
}

@Preview
@Composable
fun GoalsPreview(){
    GoalsComposable()
}