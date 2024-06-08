package com.example.collectorapp.ui.screens.Items
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.google.accompanist.pager.ExperimentalPagerApi


@Composable
fun AddingItems(addItemsViewModel: AddItemsViewModel,
                addCategoryViewModel: AddCategoryViewModel){
    val context = LocalContext.current
    Box(modifier = Modifier.padding(40.dp)){

        LazyColumn {
            item {
                LazyRow {
                    item {
                       AddingItems(addItemsViewModel = addItemsViewModel, addCategoryViewModel = addCategoryViewModel)
                    }
                    item{
                        MyItemsCard(
                            addItemsViewModel = addCategoryViewModel,
                            painter = painterResource(id = R.drawable.cto),
                            contentDescription = addItemsViewModel.itemsState.value!!.itemDescription,
                            title = addItemsViewModel.itemsState.value!!.itemName,
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
                    itemName = addItemsViewModel.itemsState.value!!.itemName,
                    itemBrand = addItemsViewModel.itemsState.value!!.itemBrand,
                    itemDescription = addItemsViewModel.itemsState.value!!.itemDescription,
                    yearOfAcquisition = addItemsViewModel.itemsState.value!!.yearOfAcquisition,
                    itemCategory = addItemsViewModel.itemsState.value!!.itemCategory,
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
            value = addItemsViewModel.itemsState.value!!.itemName,
            onValueChange = {addItemsViewModel.updateItemName(it)},
            label = { Text(text = "Enter Item Name")}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addItemsViewModel.itemsState.value!!.itemBrand,
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
                value = addItemsViewModel.itemsState.value!!.itemDescription,
                onValueChange = {addItemsViewModel.updateDescription(it)},
                label = { Text(text = "Enter Item Description")}
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addItemsViewModel.itemsState.value!!.yearOfAcquisition,
            onValueChange = {addItemsViewModel.updateYOA(it)},
            label = { Text(text = "Enter Item Name")}
        )
    }
}