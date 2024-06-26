package com.example.collectorapp.ui.screens.Startup.CTA

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.collectorapp.R

@Composable
fun CallToAction(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.cto),
            contentDescription = "CTO",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())
        WelcomeText(navController)
    }
}

@Composable
fun WelcomeText(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()){
        Spacer(
            modifier = Modifier.height(200.dp)
        )
        Text(text = "Welcome to \nItemHub",
            fontSize = 70.sp,
            lineHeight = 70.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Start,
            color = Color.White
        )
        Spacer(
            modifier = Modifier.height(125.dp)
        )
        Buttons( navController )
    }
}
@Composable
fun Buttons(navController: NavController){
    Row(modifier = Modifier.padding(30.dp),
        horizontalArrangement  = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { navController.navigate("login_interface") }) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.padding(end = 50.dp))
        OutlinedButton(onClick = { navController.navigate("registration_interface") },
        ) {
            Text(text = "Create Account",
                color = Color.White)
        }
    }
}