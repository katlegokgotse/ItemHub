package com.example.itemhubjetpack.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.theme.componentShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue:String, painter: Painter){
    val textValue = remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = componentShapes.medium),
        value = textValue.value,
        label = {
            Text(text = labelValue)
        },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
        ),
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon(painter = painter, contentDescription = "Profile")
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPasswordTextField(labelValue:String, painter: Painter){
    val textValue = remember { mutableStateOf("") }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = componentShapes.medium),
        value = textValue.value,
        label = {
            Text(text = labelValue)
        },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.password), contentDescription = "Profile")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Check
            }else{
                Icons.Filled.Clear
            }

            var description = if(passwordVisible.value){
                "Hide password"
            }else{
                "Show password"
            }
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(labelValue: String, painter: Painter){
    Column(){
        val itemsViewModel: AddItemsViewModel= AddItemsViewModel()
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = componentShapes.medium),
            value = textValue.value,
            label = {
                Text(text = labelValue)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions.Default,
            onValueChange = {
                textValue.value = it
            },
            leadingIcon = {
                Icon(painter = painter, contentDescription = "Profile")
            })
    }
}*/


