package com.example.collectorapp.ui.screens.Authentication.Signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.collectorapp.ui.screens.Authentication.SignIn.GoogleButton
import com.example.collectorapp.ui.screens.Authentication.UserRegistration

@Composable
fun RegisterInterface(authenticationViewModel: AuthenticationViewModel, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.cto),
            contentDescription = "Login",
            contentScale = ContentScale.Crop
        )
        RegisterComponents(authenticationViewModel, navController)
    }
}

@Composable
fun RegisterComponents(authenticationViewModel: AuthenticationViewModel, navController: NavController) {
    val context = LocalContext.current
    val userReg by authenticationViewModel.userReg.collectAsState()

    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Create An Account",
            fontSize = 48.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        GoogleButton("Sign up using Google")
        Spacer(modifier = Modifier.padding(10.dp))
        RegisterUserInput(
            firstName = userReg.firstName,
            lastName = userReg.lastName,
            email = userReg.email,
            password = userReg.password,
            confirmPassword = confirmPassword,
            onFirstNameChange = { authenticationViewModel.updateUserFirstName(it) },
            onLastNameChange = { authenticationViewModel.updateUserLastName(it) },
            onEmailChange = { authenticationViewModel.updateUserEmailReg(it) },
            onPasswordChange = { authenticationViewModel.updateUserPasswordReg(it) },
            onConfirmPasswordChange = { confirmPassword = it }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        RegisterButton(onClick = {
            when {
                userReg.firstName.isEmpty() -> {
                    Toast.makeText(context, "Enter your first name", Toast.LENGTH_SHORT).show()
                }
                userReg.lastName.isEmpty() -> {
                    Toast.makeText(context, "Enter your last name", Toast.LENGTH_SHORT).show()
                }
                userReg.email.isEmpty() -> {
                    Toast.makeText(context, "Enter your email", Toast.LENGTH_SHORT).show()
                }
                userReg.password.isEmpty() -> {
                    Toast.makeText(context, "Password should not be empty", Toast.LENGTH_SHORT).show()
                }
                confirmPassword != userReg.password -> {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val newUser = UserRegistration(
                        firstName = userReg.firstName,
                        lastName = userReg.lastName,
                        email = userReg.email,
                        password = userReg.password
                    )
                    authenticationViewModel.addUserRegistrationList(newUser)
                    navController.navigate("login_interface")
                }
            }
        })
    }
}

@Composable
fun RegisterUserInput(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    confirmPassword: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text(text = "First Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text(text = "Last Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = { Text(text = "Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text(text = "Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = "Register")
    }
}
