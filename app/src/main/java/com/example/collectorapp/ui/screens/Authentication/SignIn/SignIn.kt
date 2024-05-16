package com.example.collectorapp.ui.screens.Authentication.SignIn

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorapp.R
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collectorapp.MyBottomAppBar
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Authentication.SignIn.ui.theme.CollectorAppTheme
import com.example.collectorapp.ui.screens.Authentication.Signup.RegisterInterface
import com.example.collectorapp.ui.screens.Authentication.Signup.authenticationViewModel
import com.example.collectorapp.ui.screens.Authentication.Signup.textState
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Categories.AddNewCategories
import com.example.collectorapp.ui.screens.Categories.CreateCategories
import com.example.collectorapp.ui.screens.Categories.UserCategoryInput

class SignIn : ComponentActivity() {
    var signInViewModel: AuthenticationViewModel = AuthenticationViewModel()
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
fun LoginInterface(s: AuthenticationViewModel, navController: NavController){
    Box(){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.cto),
            contentDescription = "Login",
            contentScale = ContentScale.Crop
        )
        Components(s, navController)
    }
}

@Composable
fun Components(s: AuthenticationViewModel,  navController: NavController){
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
        UserInput(s, navController)
    }
}

@Composable
fun UserInput(s: AuthenticationViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = s._loginState.value.email,
            onValueChange = {s.updateUserEmail(it)},
            label = { Text(text = "Email Address")},
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = s._loginState.value.password,
            onValueChange = {s.updateUserPassword(it)},
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        LoginButton(onClick = {
            if (authenticationViewModel._loginState.value.email.isEmpty()){
                // Toast.makeText(context = this, "Enter your last name", Toast.LENGTH_SHORT).show()
            }
            else if (authenticationViewModel._loginState.value.password.isEmpty()){
                // Toast.makeText(this, "Password should not be empty", Toast.LENGTH_SHORT).show()
            }
            else if (authenticationViewModel._userReg.value.email != authenticationViewModel._userReg.value.email){
                //
            }
            else if (authenticationViewModel._userReg.value.password != authenticationViewModel._loginState.value.password){
                //
            }else{
                        navController.navigate("home")
            }
        })
    }
}
@Composable
fun LoginButton(onClick: () -> Unit){
    Button(onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = "Login")
    }
}
@Composable
fun GoogleButton(text: String){
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
        Text(text = text,
            textAlign = TextAlign.Center)
    }
}
@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
   CollectorAppTheme {
       val signInViewModel: AuthenticationViewModel = AuthenticationViewModel()
       //LoginInterface(signInViewModel)
    }
}

