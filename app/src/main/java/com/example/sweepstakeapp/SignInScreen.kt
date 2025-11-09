package com.example.sweepstakeapp

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSignInWithEmailClicked: (String, String) -> Unit,
    onContinueAnonymouslyClicked: () -> Unit
) {
    val welcomeViewModel: WelcomeViewModel = viewModel()

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign In or Register")

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = welcomeViewModel.email,
            onValueChange = { welcomeViewModel.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = welcomeViewModel.password,
            onValueChange = { welcomeViewModel.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { 
                Log.d("SignInScreen", "Sign In/Register with Email clicked.")
                onSignInWithEmailClicked(welcomeViewModel.email, welcomeViewModel.password) 
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In / Register with Email")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { 
                Log.d("SignInScreen", "Continue Anonymously clicked.")
                onContinueAnonymouslyClicked() 
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue Anonymously")
        }
    }
}
