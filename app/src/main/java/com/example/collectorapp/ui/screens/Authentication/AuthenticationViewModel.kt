package com.example.collectorapp.ui.screens.Authentication
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthenticationViewModel: ViewModel(){
    val _loginState = mutableStateOf(LoginScreen())
    val _userList = mutableStateOf(UserList())
    val _userReg = mutableStateOf(UserRegistration())
    val validatePassword: ValidatePassword = ValidatePassword()

    fun updateUserEmail(email: String){
        _loginState.value = _loginState.value.copy(email = email)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateUserPassword(password: String){
        viewModelScope.launch {
            val passwordError = {
                snapshotFlow { password }
                    .mapLatest { validatePassword.execute(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000),
                        initialValue = PasswordValidationState()
                    )
            }
            _loginState.value = _loginState.value.copy(password = password)

        }
       }

    //
    fun updateUserFirstName(firstName: String){
        _userReg.value = _userReg.value.copy(firstName = firstName)
    }
    fun updateUserLastName(lastName: String){
        _userReg.value = _userReg.value.copy(lastName = lastName)
    }
    fun updateUserEmailReg(regEmail: String){
        _userReg.value = _userReg.value.copy(email = regEmail)
    }
    fun updateUserPasswordReg(password: String){
        _userReg.value = _userReg.value.copy(password = password)
    }


    fun addUserRegistrationList(usersRegistration: UserRegistration){
        //
        val listOfUsers = _userList.value.usersRegistration.toMutableList()
        listOfUsers.add(usersRegistration)
        _userList.value = _userList.value.copy(usersRegistration = listOfUsers)
    }
    fun fetchUserInformation(email: String, password: String):Boolean{
        val user = _userList.value.usersRegistration.find { it.email == email && it.password == password }
        return if (user != null){
            true
        }else{
            false
        }
    }
}
data class LoginScreen(
    val email: String = "",
    val password: String = ""
)
data class PasswordValidationState(
    val hasMininmum: Boolean = false,
    val hasCapitalLetter: Boolean = false,
    val hasSpecialCharacter: Boolean = false,
    val successful: Boolean = false
)
class ValidatePassword{
    fun execute(password: String): PasswordValidationState{
        val isCapital = validateCapitalLetters(password)
        val isSpecial = validateSpecialCharacter(password)
        val isMinimum = validateMinimum(password)

        val hasError = listOf(
            isCapital, isSpecial, isMinimum
        ).all { it }

        return PasswordValidationState(
            hasMininmum = isMinimum,
            hasCapitalLetter = isCapital,
            hasSpecialCharacter = isSpecial,
            successful = hasError
        )
    }
    private fun validateSpecialCharacter(password: String): Boolean =
        password.contains(Regex("[^A-Za-z0-9 !@#\$%^&*()-_=+\\[\\]{}|;:'\",.<>/?]"))
    private fun validateCapitalLetters(password: String): Boolean =
        password.matches(Regex(".*[A-Z].*"))

    private fun validateMinimum(password: String): Boolean =
        password.matches(Regex(".{6,}"))
}
//Registration
data class UserRegistration(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = ""
)

data class UserList(
   val usersRegistration: List<UserRegistration> = mutableListOf()
)