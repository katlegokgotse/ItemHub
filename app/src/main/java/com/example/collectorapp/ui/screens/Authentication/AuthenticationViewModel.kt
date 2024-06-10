package com.example.collectorapp.ui.screens.Authentication
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class AuthenticationViewModel: ViewModel() {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val firestoreAuth = Firebase.firestore
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
        addUserToFirestore(
            userId = auth.currentUser!!.uid,
            name = usersRegistration.firstName,
            lastName = usersRegistration.lastName,
            email = usersRegistration.email,
            password = usersRegistration.password
        )
    }
    private fun createUser(email: String, password: String, onSuccess: (FirebaseUser?) -> Unit, onFailure: (Exception?) -> Unit) {
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

    private fun addUserToDatabase(userId: String?, name: String, lastName: String, email: String, password: String) {
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
    private fun addUserToFirestore(userId: String?, name: String, lastName: String, email: String, password: String) {
        val user = UserRegistration(
            firstName = name,
            lastName = lastName,
            email = email,
            password = password
        )
        val userId = firestoreAuth.collection("users").document().id
        userId.let {id ->
            firestoreAuth.collection("users").document(id).set(user)
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

    fun signInUserWithFirestore(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val querySnapshot = firestoreAuth.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                if (querySnapshot.isEmpty) {
                    Log.d(TAG, "No user found with email: $email")
                    callback(false) // No user found
                } else {
                    val userDocument = querySnapshot.documents.first()
                    val storedPassword = userDocument.getString("password")
                    if (password == storedPassword) {
                        Log.d(TAG, "User signed in successfully")
                        callback(true) // Successful sign-in
                    } else {
                        Log.d(TAG, "Incorrect password for email: $email")
                        callback(false) // Incorrect password
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting docs", e)
                callback(false) // Error occurred
            }
        }
    }

    /*private fun signInUser(
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
                    signInUserWithFirestore(email, password) { success ->
                        if (success) {
                            navController.navigate("home")
                        } else {
                            // Handle unsuccessful sign-in (e.g., display an error message)
                        }
                    }
                    onSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    onFailure(task.exception)
                }
            }
    }*/

    fun fetchUserInformation(email: String, password: String, function: () -> Nothing, ) {
        val userList = _userList.value
       /* signInUser(email,
            password,
            onSuccess = { _ -> Log.d(TAG, "createUserWithEmail:success") },
            onFailure = { _ -> Log.d(TAG, "createUserWithEmail:failure") })*/
        //return userList.usersRegistration.any { it.email == email && it.password == password }
    }

    fun displayUserName() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            val userRef = firestoreAuth.collection("users").document(userId)

            fun displayUserName() {
                viewModelScope.launch {
                    val userId = auth.currentUser?.uid ?: return@launch
                    val userRef = firestoreAuth.collection("users").document(userId)

                    try {
                        val documentSnapshot = userRef.get().await()
                        if (documentSnapshot.exists()) {
                            val user = documentSnapshot.toObject(UserRegistration::class.java)
                            val userName = user?.firstName ?: "No Name"
                            _userName.value = userName
                            Log.d(TAG, "Fetched user name: $userName")
                        } else {
                            Log.d(TAG, "No such document in Firestore")
                            _userName.value = "No Name"
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error fetching user name", e)
                        _userName.value = "No name"
                    }
                }
            }

        }
    }


}
