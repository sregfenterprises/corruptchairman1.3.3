package com.sregfenterprises.corruptchairman.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sregfenterprises.corruptchairman.R

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        // Background image (optional, remove if not needed)
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Title text
        Text(
            text = "Corrupt Chairman",
            fontSize = 48.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 150.dp)
        )

        // Bottom button
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Continue")
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
