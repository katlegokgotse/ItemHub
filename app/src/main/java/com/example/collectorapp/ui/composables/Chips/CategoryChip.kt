package com.example.collectorapp.ui.composables.Chips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryChips(
    category: String,
    onExecuteSearch: () -> Unit,
){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Button(
            modifier = Modifier,
            onClick = { onExecuteSearch()}
        ){
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
        }
    }
}