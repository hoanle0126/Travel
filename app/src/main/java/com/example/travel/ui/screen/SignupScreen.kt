package com.example.travel.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel.R
import com.example.travel.data.controller.AuthController

@Composable
fun SignupScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = "Signup Image",
            modifier = Modifier.size(200.dp)
        )

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Nice To Meet You", style = MaterialTheme.typography.titleLarge)

            Text(text = "Sign up your account", style = MaterialTheme.typography.bodySmall)
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email address",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            maxLines = 1,
            label = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = {
                Text(
                    text = "Confirm Password",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = {
                AuthController(context, navController).signup(
                    name,
                    email,
                    password,
                    confirmPassword
                )
            },
        ) {
            Text(text = "Sign Up", style = MaterialTheme.typography.titleMedium)
        }

        Row {
            Text(
                text = "Already have account? ",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier
                    .clickable { navController.navigate("login") },
                text = "Login",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

