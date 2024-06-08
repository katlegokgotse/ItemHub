package com.example.collectorapp.ui.screens.Items

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel

@Composable
fun MyItemsCard(
    addItemsViewModel: AddCategoryViewModel,
    imageUri: Uri?,
    contentDescription: String,
    title: String,
    modifier: Modifier){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ){
        Column {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                //elevation = 5.dp
            ){
                Box(
                    modifier = Modifier.height(200.dp)
                ){
                    imageUri?.let {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUri),
                            contentDescription = contentDescription,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center,
                    ){
                       // Text(text = addItemsViewModel._categoryState.value.categoryName, color = Color.White)
                    }
                }
            }
        }
    }

}
@Composable
fun MyCategoryCard(
    addItemsViewModel: AddCategoryViewModel,
    painter: Painter, contentDescription: String,
    title: String, modifier: Modifier){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ){
        Column {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                //elevation = 5.dp
            ){
                Box(
                    modifier = Modifier.height(200.dp)
                ){
                    Image(
                        painter = painter,
                        contentDescription = contentDescription,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center,
                    ){
                        Text(text = addItemsViewModel._categoryState.value.categoryName, color = Color.White)
                    }
                }
            }
        }
    }

}