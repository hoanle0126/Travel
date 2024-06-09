package com.example.travel.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel.R
import com.example.travel.data.model.Firebase.Place
import com.example.travel.data.`object`.AuthViewModel
import com.example.travel.data.`object`.PlaceObject
import com.example.travel.data.`object`.RestaurantObject
import com.example.travel.ui.component.RatingCustom
import com.example.travel.ui.layout.DefaultLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val places: PlaceObject = viewModel()
    val restaurants: RestaurantObject = viewModel()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://travel-f4cbd-default-rtdb.asia-southeast1.firebasedatabase.app")
    val databaseReference: DatabaseReference = database.reference.child("Places")
    val recentPlaces = remember { mutableStateOf<List<Place>>(emptyList()) }
    val authViewModel: AuthViewModel = viewModel()
    val currentUserEmail = authViewModel.user?.email

    LaunchedEffect(Unit) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val placesList = mutableListOf<Place>()
                for (placeSnapshot in snapshot.children) {
                    val place = placeSnapshot.getValue(Place::class.java)
                    if (place != null && place.user == currentUserEmail) {
                        placesList.add(place)
                    }
                }
                recentPlaces.value = placesList
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    DefaultLayout(
        navController = navController,
        contentSpacing = 30.dp
    ) {
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    shape = RoundedCornerShape(
                        bottomEnd = 16.dp, bottomStart = 16.dp
                    )
                )
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
                )
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
//            Avatar
            Row {
                Spacer(modifier = Modifier.weight(1f))
                AsyncImage(
                    model = "https://htmediagroup.vn/wp-content/uploads/2022/04/Anh-CV-chuyen-nghiep-min-1.jpg",
                    contentDescription = "",
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer.copy(0.4f),
                            RoundedCornerShape(99.dp)
                        )
                        .clip(RoundedCornerShape(99.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }
//            End avatar
//            Title
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Khám phá",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
//            End title
//            Start category
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ion_bed_outline),
                        contentDescription = ""
                    )
                    Text(text = "Khách sạn", style = MaterialTheme.typography.headlineLarge)
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ion_ticket_outline),
                        contentDescription = ""
                    )
                    Text(
                        text = "Hoạt động\ngiải trí", style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ion_restaurant_outline),
                        contentDescription = ""
                    )
                    Text(text = "Nhà hàng", style = MaterialTheme.typography.headlineLarge)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                )
            }
//            End category
        }
//            Đã xem gần đây
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Đã xem gần đây", style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                recentPlaces.value.take(12).forEach { place ->
                    Column(
                        modifier = Modifier
                            .width(180.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .clickable { navController.navigate("details/${place.business_id.toString()}/${place.ltn.toString()}/${place.lng.toString()}") }
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(0.3f),
                                    RoundedCornerShape(8.dp)
                                ),
                            contentScale = ContentScale.FillBounds,
                            model = place.photo,
                            contentDescription = ""
                        )
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            RatingCustom(
                                value = place.rating.toInt()
                            )
                            Text(
                                text = "${place.rating}",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        Text(
                            text = "${place.type}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "${place.city}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
//
//        Khám phá thêm tại
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(12.dp)
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Khám phá thêm tại\nHuế",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary
            )
            OutlinedButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(
                    text = "Tiếp tục khám phá",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
//
//            Khách sạn giá rẻ gần bạn
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Khách sạn giá rẻ gần\nbạn",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Xem thêm",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(
                text = "Đặt phòng tại những khách sạn này",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                 places.placeResult?.data.orEmpty().take(12).forEach { place ->
                     Column(
                         modifier = Modifier
                             .clickable {
                                 navController.navigate("details/${place.business_id}/${place.latitude.toString()}/${place.longitude.toString()}")
                                 val placeUpdates = mapOf(
                                     "business_id" to place.business_id,
                                     "name" to place.name,
                                     "rating" to place.rating,
                                     "type" to place.types?.firstOrNull().orEmpty(),
                                     "city" to place.city,
                                     "photo" to place.photos?.firstOrNull()?.src.orEmpty(),
                                     "ltn" to place.latitude.toString(),
                                     "lng" to place.longitude.toString(),
                                     "user" to currentUserEmail
                                 )
                                 place.business_id?.let { databaseReference.child(it).setValue(placeUpdates) }
                             }
                             .width(180.dp),
                         verticalArrangement = Arrangement.spacedBy(4.dp)
                     ) {
                         place.photos?.take(1)?.forEach{ photo ->
                             AsyncImage(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .height(120.dp)
                                     .clip(RoundedCornerShape(8.dp))
                                     .background(
                                         MaterialTheme.colorScheme.primary.copy(0.3f),
                                         RoundedCornerShape(8.dp)
                                     ),
                                 contentScale = ContentScale.FillBounds,
                                 model = photo.src,
                                 contentDescription = ""
                             )
                         }
                         place.name?.let {
                             Text(
                                 text = it,
                                 style = MaterialTheme.typography.titleSmall
                             )
                         }
                         Row(
                             verticalAlignment = Alignment.CenterVertically,
                             horizontalArrangement = Arrangement.spacedBy(8.dp)
                         ) {
                             place.rating?.let {
                                 RatingCustom(
                                     value = it.toInt()
                                 )
                             }
                             Text(
                                 text = place.rating.toString(),
                                 style = MaterialTheme.typography.headlineMedium
                             )
                         }
                         place.types?.take(1)?.forEach{ type ->
                             Text(
                                 text = type,
                                 style = MaterialTheme.typography.headlineMedium,
                                 color = MaterialTheme.colorScheme.outline
                             )
                         }
                         place.city?.let {
                             Text(
                                 text = it,
                                 style = MaterialTheme.typography.headlineMedium,
                                 color = MaterialTheme.colorScheme.outline
                             )
                         }
                     }
                 }
            }
        }
//
//            Nhà hàng lân cận
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Nhà hàng lân cận",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Xem thêm",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(
                text = "Những địa hiểm ăn uống cho chuyến\nđi của bạn",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                restaurants.placeResult?.data.orEmpty().take(12).forEach { restaurant ->
                    Column(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("details/${restaurant.business_id}/${restaurant.latitude.toString()}/${restaurant.longitude.toString()}")
                                val placeUpdates = mapOf(
                                    "business_id" to restaurant.business_id,
                                    "name" to restaurant.name,
                                    "rating" to restaurant.rating,
                                    "type" to restaurant.types?.firstOrNull().orEmpty(),
                                    "city" to restaurant.city,
                                    "photo" to restaurant.photos?.firstOrNull()?.src.orEmpty(),
                                    "ltn" to restaurant.latitude.toString(),
                                    "lng" to restaurant.longitude.toString(),
                                    "user" to currentUserEmail
                                )
                                restaurant.business_id?.let { databaseReference.child(it).setValue(placeUpdates) }
                            }
                            .width(180.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        restaurant.photos?.take(1)?.forEach{ photo ->
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(0.3f),
                                        RoundedCornerShape(8.dp)
                                    ),
                                contentScale = ContentScale.FillBounds,
                                model = photo.src,
                                contentDescription = ""
                            )
                        }
                        restaurant.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            restaurant.rating?.let {
                                RatingCustom(
                                    value = it.toInt()
                                )
                            }
                            Text(
                                text = restaurant.rating.toString(),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        restaurant.types?.take(1)?.forEach{ type ->
                            Text(
                                text = type,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        restaurant.city?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
//
//        Các địa điểm nên đến trong mùa hè
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .paint(
                    painter = painterResource(id = R.drawable.vinh_ha_long),
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.6f
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Các địa điểm nên đến trong mùa hè",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.surface
            )
            Text(
                text = "Từ Vịnh Hạ Long đến Nha Trang",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surface
            )
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = "Đọc thêm", style = MaterialTheme.typography.titleMedium)
            }
        }
//
//  Điểm đến hàng đầu cho kì nghỉ tiếp theo của bạn
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Điểm đến hàng đầu cho\nkì nghỉ tiếp theo của bạn",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Xem thêm",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(
                text = "Đây là địa điểm thu hút nhiều khách du lịch",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(12) {
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .paint(
                                painter = painterResource(id = R.drawable.vinh_ha_long),
                                contentScale = ContentScale.FillBounds,
                                alpha = 0.8f
                            )
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Vịnh Hạ Long",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }
//
        Spacer(modifier = Modifier.height(4.dp))
    }
}

