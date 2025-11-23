package com.sregfenterprises.corruptchairman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isAgeConfirmed by mutableStateOf(false)
    var areTermsAgreed by mutableStateOf(false)
}
