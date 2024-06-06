package com.example.collectorapp.ui.screens.Authentication

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
data class LoginScreen(
    val email: String = "",
    val password: String = ""
)
data class PasswordValidationState(
    val hasMinimum: Boolean = false,
    val hasCapitalLetter: Boolean = false,
    val hasSpecialCharacter: Boolean = false,
    val successful: Boolean = false,
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
            hasMinimum = isMinimum,
            hasCapitalLetter = isCapital,
            hasSpecialCharacter = isSpecial,
            successful = hasError
        )
    }
    private fun validateSpecialCharacter(password: String): Boolean =
        password.contains(Regex(pattern = "[^A-Za-z0-9 !@#\$%^&*()-_=+\\[\\]{}|;:'\",.<>/?]"))
    private fun validateCapitalLetters(password: String): Boolean =
        password.matches(Regex(pattern = ".*[A-Z].*"))

    private fun validateMinimum(password: String): Boolean =
        password.matches(Regex(pattern = ".{6,}"))
}

sealed class SignInStatus {
    object Idle : SignInStatus()
    object Loading : SignInStatus()
    object Success : SignInStatus()
    data class Failure(val exception: Exception?) : SignInStatus()
}