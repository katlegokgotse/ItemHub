package com.example.collectorapp.ui.screens.Authentication.SignIn

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collectorapp.R
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Authentication.SignIn.ui.theme.CollectorAppTheme

class SignIn : ComponentActivity() {
    private val signInViewModel: AuthenticationViewModel by lazy { AuthenticationViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CollectorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginInterface(signInViewModel, navController)
                }
            }
        }
    }
}

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
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel._loginState.value.email,
            onValueChange = { viewModel.updateUserEmail(it) },
            label = { Text(text = "Email Address") },
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
            val loginState = viewModel._loginState.value
            val isAuthenticated = viewModel.fetchUserInformation(
                email = loginState.email,
                password = loginState.password
            )
            if (isAuthenticated) {
                navController.navigate("home")
            } else {
               /**/
            }
        })
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

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    CollectorAppTheme {
        val signInViewModel = AuthenticationViewModel()
        val navController = rememberNavController()
        LoginInterface(signInViewModel, navController)
    }
}