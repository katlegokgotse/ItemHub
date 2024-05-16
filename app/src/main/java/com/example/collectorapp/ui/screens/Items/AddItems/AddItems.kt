package com.example.collectorapp.ui.screens.Items.AddItems

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Items.AddItems.ui.theme.CollectorAppTheme
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.screens.Items.MyItems.MyItemsCard

class AddItems : ComponentActivity() {
    val addItemsViewModel: AddItemsViewModel = AddItemsViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollectorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddingItems(addItemsViewModel)

                }
            }
        }
    }
}

@Composable
fun AddingItems(addItemsViewModel: AddItemsViewModel){
    Box(modifier = Modifier.padding(40.dp)){
        LazyColumn(){
            item {
                LazyRow(){
                    item(){
                       // AddingItems(addItemsViewModel = add)
                    }
                    item{
                        MyItemsCard(
                            addItemsViewModel = addItemsViewModel,
                            painter = painterResource(id = R.drawable.cto),
                            contentDescription = addItemsViewModel._itemsState.value.itemDescription,
                            title = addItemsViewModel._itemsState.value.itemName,
                            modifier = Modifier.width(150.dp))
                    }
                }

            }

            item {
                UserItemsInput(addItemsViewModel = addItemsViewModel)
            }

            item {
                Button(onClick = { /**/ }) {
                    Text(text = "Save")
                }
            }
        }
    }

}

@Composable
fun UserItemsInput(addItemsViewModel: AddItemsViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addItemsViewModel._itemsState.value.itemName,
            onValueChange = {addItemsViewModel.updateItemName(it)},
            label = { Text(text = "Enter Item Name")}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addItemsViewModel._itemsState.value.itemBrand,
            onValueChange = {addItemsViewModel.updateBrand(it)},
            label = { Text(text = "Enter Item Brand")}
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                value = addItemsViewModel._itemsState.value.itemDescription,
                onValueChange = {addItemsViewModel.updateDescription(it)},
                label = { Text(text = "Enter Item Description")}
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addItemsViewModel._itemsState.value.yearOfAcquisition,
            onValueChange = {addItemsViewModel.updateYOA(it)},
            label = { Text(text = "Enter Item Name")}
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddItemsPreview() {
    val addItemsViewModel: AddItemsViewModel = AddItemsViewModel()
    CollectorAppTheme {
        AddingItems(addItemsViewModel)
    }
}