package com.example.sweepstakeapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

sealed class NavigationEvent {
    object NavigateToHome : NavigationEvent()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.SignedOut)
    val authState: StateFlow<AuthState> = _authState

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent = _errorEvent.asSharedFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private fun mapFirebaseError(e: Exception): String = when (e) {
        is FirebaseAuthInvalidCredentialsException -> "Invalid email or password format."
        is FirebaseAuthUserCollisionException -> "An account with this email already exists."
        else -> e.message ?: "An unknown error occurred. Please try again."
    }

    fun signInAnonymously() {
        Log.d("AuthViewModel", "Attempting anonymous sign-in...")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.signInAnonymously().await()
                Log.d("AuthViewModel", "Anonymous sign-in SUCCESS.")
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.SignedIn
                    delay(150) // Avoid navigation race condition
                    _navigationEvent.emit(NavigationEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Anonymous sign-in FAILED.", e)
                _errorEvent.emit(mapFirebaseError(e))
            }
        }
    }

    fun signInOrRegisterWithEmail(email: String, password: String) {
        Log.d("AuthViewModel", "Attempting email/password sign-in/register for: $email")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("AuthViewModel", "Attempting to CREATE user...")
                auth.createUserWithEmailAndPassword(email, password).await()
                Log.d("AuthViewModel", "User creation SUCCESS.")
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.SignedIn
                    delay(150) // Avoid navigation race condition
                    _navigationEvent.emit(NavigationEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                Log.w("AuthViewModel", "User creation FAILED. Checking if user exists...", e)
                when (e) {
                    is FirebaseAuthUserCollisionException -> {
                        try {
                            Log.d("AuthViewModel", "User exists. Attempting to SIGN IN user...")
                            auth.signInWithEmailAndPassword(email, password).await()
                            Log.d("AuthViewModel", "User sign-in SUCCESS.")
                            withContext(Dispatchers.Main) {
                                _authState.value = AuthState.SignedIn
                                delay(150) // Avoid navigation race condition
                                _navigationEvent.emit(NavigationEvent.NavigateToHome)
                            }
                        } catch (signInError: Exception) {
                            Log.e("AuthViewModel", "User sign-in FAILED.", signInError)
                            _errorEvent.emit(mapFirebaseError(signInError))
                        }
                    }
                    else -> {
                        Log.e("AuthViewModel", "An unhandled error occurred during registration.", e)
                        _errorEvent.emit(mapFirebaseError(e))
                    }
                }
            }
        }
    }

    fun signOut() {
        Log.d("AuthViewModel", "Signing out user.")
        auth.signOut()
        _authState.value = AuthState.SignedOut
    }

    fun showError(message: String) {
        viewModelScope.launch {
            Log.d("AuthViewModel", "showError called with message: $message")
            _errorEvent.emit(message)
        }
    }
}

sealed class AuthState {
    object SignedIn : AuthState()
    object SignedOut : AuthState()
}
