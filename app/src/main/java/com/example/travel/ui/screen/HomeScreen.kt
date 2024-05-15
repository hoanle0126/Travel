package com.example.travel.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travel.controller.AuthController
import com.example.travel.viewModel.AuthViewModel
import com.example.travel.viewModel.ProvinceViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val authController = AuthController(context,navController)
    val provinceResult:ProvinceViewModel = viewModel()

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    provinceResult.provinceResult.data.forEach {
                        Text(text = "${it.name}")
                    }
                }
            }
            Button(
                onClick = {authController.signOut()}
            ){
                Text(text = "Sign Out") }
        }
    }
}