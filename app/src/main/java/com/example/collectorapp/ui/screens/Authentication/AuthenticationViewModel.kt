package com.example.collectorapp.ui.screens.Authentication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthenticationViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginScreen())
    val loginState: StateFlow<LoginScreen> = _loginState

    private val _userList = MutableStateFlow(UserList())
    val userList: StateFlow<UserList> = _userList

    private val _userReg = MutableStateFlow(UserRegistration())
    val userReg: StateFlow<UserRegistration> = _userReg

    private val validatePassword: ValidatePassword = ValidatePassword()

    fun updateUserEmail(email: String) {
        _loginState.value = _loginState.value.copy(email = email)
    }

    fun updateUserPassword(password: String) {
        val passwordValidationState = validatePassword.execute(password)
        _loginState.value = _loginState.value.copy(password = password, passwordValidationState = passwordValidationState)
    }

    fun updateUserFirstName(firstName: String) {
        _userReg.value = _userReg.value.copy(firstName = firstName)
    }

    fun updateUserLastName(lastName: String) {
        _userReg.value = _userReg.value.copy(lastName = lastName)
    }

    fun updateUserEmailReg(regEmail: String) {
        _userReg.value = _userReg.value.copy(email = regEmail)
    }

    fun updateUserPasswordReg(password: String) {
        _userReg.value = _userReg.value.copy(password = password)
    }

    fun addUserRegistrationList(usersRegistration: UserRegistration) {
        val listOfUsers = _userList.value.usersRegistration.toMutableList()
        listOfUsers.add(usersRegistration)
        _userList.value = _userList.value.copy(usersRegistration = listOfUsers)
    }

    fun fetchUserInformation(email: String, password: String): Boolean {
        return _userList.value.usersRegistration.any { it.email == email && it.password == password }
    }
}

data class LoginScreen(
    val email: String = "",
    val password: String = "",
    val passwordValidationState: PasswordValidationState = PasswordValidationState()
)

data class PasswordValidationState(
    val hasMinimum: Boolean = false,
    val hasCapitalLetter: Boolean = false,
    val hasSpecialCharacter: Boolean = false,
    val successful: Boolean = false
)

class ValidatePassword {
    fun execute(password: String): PasswordValidationState {
        val hasCapital = validateCapitalLetters(password)
        val hasSpecial = validateSpecialCharacter(password)
        val hasMinimum = validateMinimum(password)

        val successful = listOf(hasCapital, hasSpecial, hasMinimum).all { it }

        return PasswordValidationState(
            hasMinimum = hasMinimum,
            hasCapitalLetter = hasCapital,
            hasSpecialCharacter = hasSpecial,
            successful = successful
        )
    }

    private fun validateSpecialCharacter(password: String): Boolean =
        password.contains(Regex("[^A-Za-z0-9 ]"))

    private fun validateCapitalLetters(password: String): Boolean =
        password.matches(Regex(".*[A-Z].*"))

    private fun validateMinimum(password: String): Boolean =
        password.matches(Regex(".{6,}"))
}

data class UserRegistration(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = ""
)

data class UserList(
    val usersRegistration: List<UserRegistration> = emptyList()
)
