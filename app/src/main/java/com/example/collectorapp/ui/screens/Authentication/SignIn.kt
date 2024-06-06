package com.example.collectorapp.ui.screens.Authentication.SignIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Authentication.SignInStatus

@Composable
fun LoginInterface(viewModel: AuthenticationViewModel, navController: NavController) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.cto),
            contentDescription = "Login",
            contentScale = ContentScale.Crop
        )
        Components(viewModel, navController)
    }
}

@Composable
fun Components(viewModel: AuthenticationViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 120.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome Back!",
            fontSize = 48.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            textAlign = TextAlign.Center,
            text = "Log into your account"
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        GoogleButton("Log into your account")
        Spacer(modifier = Modifier.padding(40.dp))
        UserInput(viewModel, navController)
    }
}

@Composable
fun UserInput(viewModel: AuthenticationViewModel, navController: NavController) {
    val context = LocalContext.current

    val signInStatus by viewModel.signInStatus

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel._loginState.value.email,
            onValueChange = { viewModel.updateUserEmail(it) },
            label = { Text(text = "Email Address") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel._loginState.value.password,
            onValueChange = { viewModel.updateUserPassword(it) },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        LoginButton(onClick = {
            viewModel.fetchUserInformation(
                email = viewModel._loginState.value.email,
                password = viewModel._loginState.value.password
            )
        })

        when (signInStatus) {
            is SignInStatus.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            is SignInStatus.Success -> {
                Toast.makeText(context, "Sign-In Successful", Toast.LENGTH_SHORT).show()
                navController.navigate("home")
            }
            is SignInStatus.Failure -> {
                val exception = (signInStatus as SignInStatus.Failure).exception
                Toast.makeText(context, "Sign-In Failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
            }

            SignInStatus.Idle -> TODO()
        }
    }
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = "Login")
    }
}

@Composable
fun GoogleButton(text: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

