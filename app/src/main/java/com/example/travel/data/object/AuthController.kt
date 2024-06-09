package com.example.travel.data.`object`

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.travel.R
import com.google.firebase.auth.FirebaseUser

class AuthController(context: Context,navController: NavController) {
    private val auth = AuthViewModel().auth
    private val navController = navController

    private val toastEmpty = Toast.makeText(
        context,
        "Empty Information",
        Toast.LENGTH_SHORT
    )

    private val toastIncorrect = Toast.makeText(
        context,
        "Incorrect Username Or Password",
        Toast.LENGTH_SHORT
    )

    private val toastSuccessful = Toast.makeText(
        context,
        "Login Successfully",
        Toast.LENGTH_SHORT
    )

    private val toastNotMatch = Toast.makeText(
        context,
        "Your Password Doesn't Match",
        Toast.LENGTH_SHORT
    )

    private val toastInvaild = Toast.makeText(
        context,
        "Invaild Password",
        Toast.LENGTH_SHORT
    )

    private val toastEmailInvaild = Toast.makeText(
        context,
        "Email already exists or invalid",
        Toast.LENGTH_SHORT
    )

    fun login(email:String,password:String){
        if(email.isEmpty()||password.isEmpty()){
            toastEmpty.show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        toastSuccessful.show()
                        val user = auth.currentUser
                        updateUI(user)
                        navController.navigate("home")

                    } else {
                        toastIncorrect.show()
                        updateUI(null)
                    }
                }
        }
    }

    fun signup(email:String,password: String,confirmPassword:String){
        if( email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty() ){
            toastEmpty.show()
        }
        else {
            if(password == confirmPassword) {
                if (password.length >= 6 && confirmPassword.length >= 6) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val user = auth.currentUser
                                updateUI(user)
                                navController.navigate("home")
                            } else {
                                toastEmailInvaild.show()
                                updateUI(null)
                            }
                        }
                } else {
                    toastInvaild.show()
                }
            } else {
                toastNotMatch.show()
            }
        }
    }

    fun signOut(){
        auth.signOut()
        toastSuccessful.show()
        navController.navigate("login")
    }

    private fun updateUI(user: FirebaseUser?) {
    }

}