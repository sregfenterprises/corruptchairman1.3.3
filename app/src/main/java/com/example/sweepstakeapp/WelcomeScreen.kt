package com.example.sweepstakeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sweepstakeapp.ui.theme.SweepstakeappTheme
import com.google.firebase.auth.FirebaseAuth

class WelcomeScreen : ComponentActivity() {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SweepstakeappTheme {
                val navController = rememberNavController()
                var isSignedIn by remember { mutableStateOf(false) }

                LaunchedEffect(isSignedIn) {
                    if (isSignedIn) {
                        navController.navigate("home") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    }
                }

                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        var isAgeConfirmed by remember { mutableStateOf(false) }
                        var showAgeErrorDialog by remember { mutableStateOf(false) }

                        if (showAgeErrorDialog) {
                            AgeErrorDialog { showAgeErrorDialog = false }
                        }

                        WelcomeContent(
                            isAgeConfirmed = isAgeConfirmed,
                            onAgeConfirmedChange = { isAgeConfirmed = it },
                            onContinueClicked = {
                                if (isAgeConfirmed) {
                                    signInAnonymously {
                                        isSignedIn = true
                                    }
                                } else {
                                    showAgeErrorDialog = true
                                }
                            }
                        )
                    }
                    composable("home") {
                        HomeScreen(
                            onSignOutClicked = {
                                auth.signOut()
                                isSignedIn = false // Reset the state to allow signing in again
                                navController.navigate("welcome") {
                                    popUpTo("home") { inclusive = true } // Clear the back stack
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun signInAnonymously(onSuccess: () -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("WelcomeScreen", "signInAnonymously:success. Triggering onSuccess.")
                    onSuccess()
                } else {
                    Log.w("WelcomeScreen", "signInAnonymously:failure", task.exception)
                }
            }
    }
}

@Composable
fun WelcomeContent(
    modifier: Modifier = Modifier,
    isAgeConfirmed: Boolean,
    onAgeConfirmedChange: (Boolean) -> Unit,
    onContinueClicked: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting(name = "User")
            
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isAgeConfirmed, onCheckedChange = onAgeConfirmedChange)
                Text("I am over the age of 18")
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = onContinueClicked) {
                Text("Continue Anonymously")
            }
        }
    }
}

@Composable
fun AgeErrorDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Age Restriction") },
        text = { Text("Users must be over the age of 18 to use the app.") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
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
        WelcomeContent(isAgeConfirmed = true, onAgeConfirmedChange = {}, onContinueClicked = {})
    }
}
