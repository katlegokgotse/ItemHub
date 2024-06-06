package com.example.collectorapp.ui.screens.Authentication
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class AuthenticationViewModel: ViewModel() {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}
    val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val _loginState = mutableStateOf(LoginScreen())
    val _userList = mutableStateOf(UserList())
    val _userReg = mutableStateOf(UserRegistration())
    val validatePassword: ValidatePassword = ValidatePassword()

    fun updateUserEmail(email: String) {
        _loginState.value = _loginState.value.copy(email = email)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateUserPassword(password: String) {
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
        //This function takes the usersRegistration list and adds the new user into it
        val listOfUsers = _userList.value.usersRegistration.toMutableList()
        listOfUsers.add(usersRegistration)
        _userList.value = _userList.value.copy(usersRegistration = listOfUsers)
       createUser(
           email = usersRegistration.email,
           password = usersRegistration.password,
           onSuccess = { _ -> Log.d(TAG, "createUserWithEmail:success") },
           onFailure = { _ -> Log.d(TAG, "createUserWithEmail:failure")})
    }

    private fun createUser(
        email: String, password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }
    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        database.reference.child("users").child(userId).setValue(user)
    }
    private fun signInUser(
        email: String, password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        onSuccess(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        onFailure(task.exception)
                    }
                }
        }
        fun fetchUserInformation(
            email: String,
            password: String,
        ) {
            val userList = _userList.value
            signInUser(email,
                password,
                onSuccess = { _ -> Log.d(TAG, "createUserWithEmail:success") },
                onFailure = { _ -> Log.d(TAG, "createUserWithEmail:failure")})
            //return userList.usersRegistration.any { it.email == email && it.password == password }
        }
    }