package com.example.sweepstakeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sweepstakeapp.ui.theme.SweepstakeappTheme
import kotlinx.coroutines.flow.collectLatest

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val messages: List<String>) : ValidationResult()
}

class WelcomeScreen : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SweepstakeApp", "WelcomeScreen onCreate started")
        enableEdgeToEdge()
        setContent {
            Log.d("SweepstakeApp", "setContent started")
            SweepstakeappTheme {
                val navController = rememberNavController()

                var showErrorDialog by remember { mutableStateOf(false) }
                var errorMessage by remember { mutableStateOf("") }

                LaunchedEffect(authViewModel) {
                    authViewModel.navigationEvent.collectLatest { event ->
                        Log.d("WelcomeScreen", "Navigation event received: $event")
                        if (navController.currentDestination?.route != "home") {
                            when (event) {
                                is NavigationEvent.NavigateToHome -> {
                                    Log.d("WelcomeScreen", "Navigating to home screen...")
                                    navController.navigate("home") { popUpTo("welcome") { inclusive = true } }
                                }
                            }
                        }
                    }
                }
                LaunchedEffect(authViewModel) {
                    authViewModel.errorEvent.collectLatest { message ->
                        Log.d("WelcomeScreen", "Error event received: $message")
                        errorMessage = message
                        showErrorDialog = true
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController, 
                        startDestination = "welcome",
                        modifier = Modifier.fillMaxSize(),
                        enterTransition = { fadeIn() },
                        exitTransition = { fadeOut() }
                    ) {
                        composable("welcome") {
                            var isAgeConfirmed by remember { mutableStateOf(false) }
                            var areTermsAgreed by remember { mutableStateOf(false) }

                            WelcomeContent(
                                modifier = Modifier.padding(innerPadding),
                                isAgeConfirmed = isAgeConfirmed,
                                onAgeConfirmedChange = { isAgeConfirmed = it },
                                areTermsAgreed = areTermsAgreed,
                                onTermsAgreedChange = { areTermsAgreed = it },
                                onTermsClicked = { 
                                    Log.d("WelcomeScreen", "Terms and Conditions clicked.")
                                    navController.navigate("terms") 
                                },
                                onContinueClicked = {
                                    Log.d("WelcomeScreen", "Continue clicked.")
                                    when (val result = validate(isAgeConfirmed, areTermsAgreed)) {
                                        is ValidationResult.Success -> {
                                            Log.d("WelcomeScreen", "Validation success - navigating to signin")
                                            navController.navigate("signin")
                                        }
                                        is ValidationResult.Error -> {
                                            errorMessage = result.messages.joinToString("\n\n")
                                            showErrorDialog = true
                                            Log.w("WelcomeScreen", "Validation failed - showing dialog")
                                        }
                                    }
                                }
                            )
                        }
                        composable("signin") {
                            SignInScreen(
                                modifier = Modifier.padding(innerPadding),
                                authViewModel = authViewModel,
                                onSignInWithEmailClicked = { email, password ->
                                    when(val result = validate(true, true, email, password)) {
                                        is ValidationResult.Success -> authViewModel.signInOrRegisterWithEmail(email, password)
                                        is ValidationResult.Error -> {
                                            errorMessage = result.messages.joinToString("\n\n")
                                            showErrorDialog = true
                                        }
                                    }
                                },
                                onContinueAnonymouslyClicked = { authViewModel.signInAnonymously() }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                onSignOutClicked = { 
                                    Log.d("WelcomeScreen", "Sign Out clicked.")
                                    authViewModel.signOut()
                                    navController.navigate("welcome") { popUpTo("home") { inclusive = true } }
                                }
                            )
                        }
                        composable("terms") {
                            TermsAndConditionsScreen(
                                modifier = Modifier.padding(innerPadding),
                                onReturnClicked = { 
                                    Log.d("WelcomeScreen", "Return from Terms clicked.")
                                    navController.popBackStack() 
                                }
                            )
                        }
                    }
                }

                if (showErrorDialog) {
                    ErrorDialog(message = errorMessage, onDismiss = { 
                        Log.d("WelcomeScreen", "Error dialog dismissed.")
                        showErrorDialog = false 
                    })
                }
            }
        }
    }

    private fun validate(
        isAgeConfirmed: Boolean, 
        areTermsAgreed: Boolean, 
        email: String? = null, 
        password: String? = null
    ): ValidationResult {
        val errors = mutableListOf<String>()
        if (!isAgeConfirmed) errors.add("Users must be over the age of 18 to use the app.")
        if (!areTermsAgreed) errors.add("You must agree to the terms and conditions.")
        // Email/password validation is now only relevant on the sign-in screen
        if (email != null && email.isBlank()) errors.add("Email cannot be blank.")
        if (password != null && password.isBlank()) errors.add("Password cannot be blank.")

        return if (errors.isEmpty()) {
            Log.d("WelcomeScreen", "Validation SUCCESS.")
            ValidationResult.Success
        } else {
            Log.w("WelcomeScreen", "Validation FAILED with errors: ${errors.joinToString()}")
            ValidationResult.Error(errors)
        }
    }
}

@Composable
fun WelcomeContent(
    modifier: Modifier = Modifier,
    isAgeConfirmed: Boolean,
    onAgeConfirmedChange: (Boolean) -> Unit,
    areTermsAgreed: Boolean,
    onTermsAgreedChange: (Boolean) -> Unit,
    onTermsClicked: () -> Unit,
    onContinueClicked: () -> Unit
) {
    Log.d("SweepstakeApp", "WelcomeContent rendering")
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "User")

        Spacer(Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isAgeConfirmed, onCheckedChange = onAgeConfirmedChange)
            Text("I am over the age of 18")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = areTermsAgreed, onCheckedChange = onTermsAgreedChange)
            Text("Agree to ")
            Text(
                text = "Terms and Conditions",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = onTermsClicked)
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(onClick = onContinueClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Continue")
        }
    }
}

@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Action Required") },
        text = { Text(message) },
        confirmButton = { Button(onClick = onDismiss) { Text("OK") } }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to the Sweepstake App, $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SweepstakeappTheme {
        WelcomeContent(
            modifier = Modifier.padding(16.dp), 
            isAgeConfirmed = true, 
            onAgeConfirmedChange = {}, 
            areTermsAgreed = true, 
            onTermsAgreedChange = {}, 
            onTermsClicked = {}, 
            onContinueClicked = {}
        )
    }
}
