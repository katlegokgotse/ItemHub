package com.example.collectorapp.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GoogleAuthButton(textValue: String, painter: Painter, onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(painter = painter, contentDescription = "Sign in using google")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = textValue,
            textAlign = TextAlign.Center)
    }
}

@Composable
fun AuthButton(textValue: String, onClick: ()-> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = textValue,
            textAlign = TextAlign.Center)
    }
}