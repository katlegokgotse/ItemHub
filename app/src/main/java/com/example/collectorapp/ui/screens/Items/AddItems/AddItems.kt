package com.example.collectorapp.ui.screens.Items.AddItems

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Authentication.UserRegistration
import com.example.collectorapp.ui.screens.Items.AddItems.ui.theme.CollectorAppTheme
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.screens.Items.ItemInformation
import com.example.collectorapp.ui.screens.Items.MyItems.MyItemsCard
import com.google.accompanist.pager.ExperimentalPagerApi

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

        LazyColumn {
            item {
                LazyRow {
                    item {
                       AddingItems(addItemsViewModel = addItemsViewModel)
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
                    Button(onClick = { /**/ }) {
                        Text(text = "Save")
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun AddItemsTopSection(onSave: () -> Unit = {}, onCancel: () -> Unit = {}, addItemsViewModel: AddItemsViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = Color.Transparent),
    ) {
        // Back button
        IconButton(onClick = {},
            modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, contentDescription = null)
        }

        // Skip Button
        TextButton(
            onClick = {
                val newItems = ItemInformation(
                    itemName = addItemsViewModel._itemsState.value.itemName,
                    itemBrand = addItemsViewModel._itemsState.value.itemBrand,
                    itemDescription = addItemsViewModel._itemsState.value.itemDescription,
                    yearOfAcquisition = addItemsViewModel._itemsState.value.yearOfAcquisition,
                    itemCategory = addItemsViewModel._itemsState.value.itemCategory,
                )
                      val saveValue = addItemsViewModel.saveItem(newItems)
            },
            modifier = Modifier.align(Alignment.CenterEnd),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Save", color = MaterialTheme.colorScheme.onBackground)
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