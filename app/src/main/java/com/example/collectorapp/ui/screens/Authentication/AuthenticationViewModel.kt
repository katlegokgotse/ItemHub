package com.example.collectorapp.ui.screens.Authentication
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.google.firebase.database.FirebaseDatabase

class AuthenticationViewModel: ViewModel() {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val _loginState = mutableStateOf(LoginScreen())
    val _userList = mutableStateOf(UserList())
    val _userReg = mutableStateOf(UserRegistration())
    val validatePassword: ValidatePassword = ValidatePassword()

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

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
            _loginState.value = _loginState.value.copy(password = password.hashCode().toString())
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
        if (usersRegistration.password.length < 6) {
            Log.e(TAG, "Password must be at least 6 characters long")
            return
        }

        if (usersRegistration.firstName.isBlank() || usersRegistration.lastName.isBlank()) {
            Log.e(TAG, "First name and last name cannot be blank")
            return
        }
        val listOfUsers = _userList.value.usersRegistration.toMutableList()
        listOfUsers.add(usersRegistration)
        _userList.value = _userList.value.copy(usersRegistration = listOfUsers)
        createUser(
            email = usersRegistration.email,
            password = usersRegistration.password,
            onSuccess = { _ -> Log.d(TAG, "createUserWithEmail:success") },
            onFailure = { _ -> Log.d(TAG, "createUserWithEmail:failure") }
        )
        addUserToDatabase(
            userId = auth.currentUser!!.uid,
            name = usersRegistration.firstName,
            lastName = usersRegistration.lastName,
            email = usersRegistration.email,
            password = usersRegistration.password
        ) //Creating the database instance in firebase database

    }

    private fun createUser(
        email: String,
        password: String,
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
                        user?.sendEmailVerification()?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d(TAG, "Email sent.")
                            } else {
                                Log.d(TAG, "Email Cant send.")
                            }
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }

    private fun addUserToDatabase(
        userId: String?,
        name: String,
        lastName: String,
        email: String,
        password: String
    ) {
        val user = UserRegistration(
            firstName = name,
            lastName = lastName,
            email = email,
            password = password
        )
        val userId = database.reference.child("users").push().key
        if (userId != null) {
            database.reference.child("users").child(userId).setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // The operation was successful
                        Log.d(TAG, "User data saved successfully")
                    } else {
                        // The operation failed
                        task.exception?.let {
                            Log.e(TAG, "Error saving user data", it)
                        }
                    }
                }
        }
    }

    private fun signInUser(
        email: String,
        password: String,
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

    fun fetchUserInformation(email: String, password: String, function: () -> Nothing, ) {
        val userList = _userList.value
        signInUser(email,
            password,
            onSuccess = { _ -> Log.d(TAG, "createUserWithEmail:success") },
            onFailure = { _ -> Log.d(TAG, "createUserWithEmail:failure") })
        //return userList.usersRegistration.any { it.email == email && it.password == password }
    }

    fun displayUserName() {
        val userId = auth.currentUser?.uid ?: return
        val userRef = database.reference.child("users").child(userId)

        userRef.get().addOnSuccessListener { dataSnapshot ->
            val user = dataSnapshot.getValue(UserRegistration::class.java)
            val userName = user?.firstName ?: "No Name"
           _userName.value = userName
        }.addOnFailureListener { exception ->
            Log.e(TAG, "Error fetching user name", exception)
           _userName.value = "No name"
        }
    }
}
