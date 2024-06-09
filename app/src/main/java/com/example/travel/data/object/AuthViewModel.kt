package com.example.travel.data.`object`

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class AuthViewModel:ViewModel() {
    val auth = Firebase.auth

    val user = auth.currentUser
}