package com.example.collectorapp.ui.screens.Authentication
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                        initialValue = PasswordValidationState(hasMinimum = false)
                    )
            }
            _loginState.value = _loginState.value.copy(password = password)
        }
       }
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
        //This function takes the usersRegistration list and adds the new user into it
        val listOfUsers = _userList.value.usersRegistration.toMutableList()
        listOfUsers.add(usersRegistration)
        _userList.value = _userList.value.copy(usersRegistration = listOfUsers)
    }
    fun fetchUserInformation(
        email: String,
        password: String
    ): Boolean{
        val userList = _userList.value
        return userList.usersRegistration.any{ it.email == email && it.password == password }
    }
}