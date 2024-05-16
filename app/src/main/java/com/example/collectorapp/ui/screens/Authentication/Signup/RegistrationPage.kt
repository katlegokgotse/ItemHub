package com.example.collectorapp.ui.screens.Authentication.Signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

val authenticationViewModel: AuthenticationViewModel = AuthenticationViewModel()
val textState: MutableState<String> = mutableStateOf("");
@Composable
fun RegisterInterface(authenticationViewModel: AuthenticationViewModel, navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
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
fun RegisterComponents(authenticationViewModel: AuthenticationViewModel, navController: NavController){
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
        RegisterUserInput(authenticationViewModel, navController)
    }
}
@Composable
fun RegisterUserInput( authenticationViewModel: AuthenticationViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = authenticationViewModel._userReg.value.firstName,
            onValueChange = {authenticationViewModel.updateUserFirstName(it)},
            label = { Text(text = "First Name")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =  authenticationViewModel._userReg.value.lastName,
            onValueChange = { authenticationViewModel.updateUserLastName(it) },
            label = { Text(text = "Last Name")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = authenticationViewModel._userReg.value.email,
            onValueChange = { authenticationViewModel.updateUserEmailReg(it)},
            label = { Text(text = "Email Address")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = authenticationViewModel._userReg.value.password,
            onValueChange = {
                            authenticationViewModel.updateUserPasswordReg(it)
                            },
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { textState.value = it},
            label = { Text(text = "Confirm Password")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
       Spacer(modifier = Modifier.padding(10.dp))
        RegisterButton(onClick = {
            if (authenticationViewModel._userReg.value.firstName.isEmpty()){
               // Toast.makeText(context = this, "Enter your first name", Toast.LENGTH_SHORT).show()
            }else if (authenticationViewModel._userReg.value.lastName.isEmpty()){
                //Toast.makeText(context = this, "Enter your last name", Toast.LENGTH_SHORT).show()
            }
            else if (authenticationViewModel._userReg.value.email.isEmpty()){
               // Toast.makeText(context = this, "Enter your last name", Toast.LENGTH_SHORT).show()
            }
            else if (authenticationViewModel._userReg.value.password.isEmpty()){
                   // Toast.makeText(this, "Password should not be empty", Toast.LENGTH_SHORT).show()
                   }
            else if (textState.value != authenticationViewModel._userReg.value.password){
                //
            }
            else{
                val newUser = UserRegistration(
                    authenticationViewModel._userReg.value.firstName,
                    authenticationViewModel._userReg.value.lastName,
                    authenticationViewModel._userReg.value.email,
                    authenticationViewModel._userReg.value.password,
                )
                authenticationViewModel.addUserRegistrationList(newUser)
                navController.navigate("login_interface")
            }
        })
    }
}

@Composable
fun RegisterButton(onClick: ()->Unit){
    Button(onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = "Register")
    }
}