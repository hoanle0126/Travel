package com.example.travel.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel.data.model.Firebase.Place
import com.example.travel.data.`object`.AuthViewModel
import com.example.travel.data.`object`.NTObject
import com.example.travel.ui.component.RatingCustom
import com.example.travel.ui.layout.DefaultLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Composable
fun NTScreen(navController: NavController) {
    val viewmore: NTObject = viewModel()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://travel-f4cbd-default-rtdb.asia-southeast1.firebasedatabase.app")
    val databaseReference: DatabaseReference = database.reference.child("Places")
    val recentPlaces = remember { mutableStateOf<List<Place>>(emptyList()) }
    val authViewModel: AuthViewModel = viewModel()
    val currentUserEmail = authViewModel.user?.email

    DefaultLayout(
        navController = navController,
        contentSpacing = 20.dp,
        topAppBar = true
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            viewmore.nearbyResult?.data?.forEach { viewmore ->
                Row(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("details/${viewmore.business_id}/${viewmore.latitude.toString()}/${viewmore.longitude.toString()}")
                            val viewmoreUpdates = mapOf(
                                "business_id" to viewmore.business_id,
                                "name" to viewmore.name,
                                "rating" to viewmore.rating,
                                "type" to viewmore.types?.firstOrNull().orEmpty(),
                                "city" to viewmore.city,
                                "photo" to viewmore.photos?.firstOrNull()?.src.orEmpty(),
                                "ltn" to viewmore.latitude.toString(),
                                "lng" to viewmore.longitude.toString(),
                                "user" to currentUserEmail
                            )
                            viewmore.business_id?.let { databaseReference.child(it).setValue(viewmoreUpdates) }
                        }
                        .fillMaxWidth()
                        .padding(5.dp)
                        .height(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewmore.photos?.take(1)?.forEach { photo ->
                        AsyncImage(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop,
                            model = photo.src,
                            contentDescription = ""
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = viewmore.name.orEmpty(),
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,

                            )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RatingCustom(
                                value = viewmore.rating?.toInt() ?: 0
                            )
                            Text(
                                text = viewmore.rating?.toString() ?: "",
                                style = MaterialTheme.typography.headlineMedium,

                                )
                        }
                        Text(
                            text = viewmore.types?.firstOrNull().orEmpty(),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Text(
                            text = viewmore.city.orEmpty(),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    }
                }
            }
        }
    }
}

