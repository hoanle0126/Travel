package com.example.travel.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel.R
import com.example.travel.data.model.Firebase.Place
import com.example.travel.data.`object`.AuthViewModel
import com.example.travel.data.`object`.DetailsObject
import com.example.travel.data.`object`.PlaceObject
import com.example.travel.data.`object`.RestaurantObject
import com.example.travel.data.`object`.SearchObject
import com.example.travel.ui.component.RatingCustom
import com.example.travel.ui.layout.DefaultLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Composable
fun SearchScreen(navController: NavController) {
    val context = LocalContext.current
    val places: PlaceObject = viewModel()
    val restaurants: RestaurantObject = viewModel()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://travel-f4cbd-default-rtdb.asia-southeast1.firebasedatabase.app")
    val databaseReference: DatabaseReference = database.reference.child("Places")
    val authViewModel: AuthViewModel = viewModel()
    val currentUserEmail = authViewModel.user?.email
    var search by remember { mutableStateOf("") }
    val currentBackStackEntry = navController.currentBackStackEntry
    val factory = SearchFactory(currentBackStackEntry?.arguments?.getString("query").toString())
    val searchs: SearchObject = viewModel(factory = factory)

    DefaultLayout(
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Tìm kiếm", style = MaterialTheme.typography.displayLarge)
//            Thanh tìm kiếm
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(99.dp),
                label = {
                    Text(
                        text = "Bạn sắp đến đâu?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_outlined_search),
                        contentDescription = ""
                    )
                },
                value = search, onValueChange = {search = it}
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                            if (search.isNotEmpty()) {
                            navController.navigate("search/$search")
                        } else {
                            Toast.makeText(context, "Please enter a search term", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(text = "Search")
                }
            }
//
//            Huy code phần giao diện hiêện mấy cái tìm kiếm ở đây
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Kết quả tìm kiếm",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        searchs.searchResult?.data.orEmpty().take(12).forEach { search ->
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("details/${search.business_id}/${search.latitude.toString()}/${search.longitude.toString()}")
                                        val placeUpdates = mapOf(
                                            "business_id" to search.business_id,
                                            "name" to search.name,
                                            "rating" to search.rating,
                                            "type" to search.types?.firstOrNull().orEmpty(),
                                            "city" to search.city,
                                            "photo" to search.photos?.firstOrNull()?.src.orEmpty(),
                                            "ltn" to search.latitude.toString(),
                                            "lng" to search.longitude.toString(),
                                            "user" to currentUserEmail
                                        )
                                        search.business_id?.let {
                                            databaseReference.child(it).setValue(placeUpdates)
                                        }
                                    }
                                    .width(180.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                search.photos?.take(1)?.forEach { photo ->
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
                                search.name?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    search.rating?.let {
                                        RatingCustom(
                                            value = it.toInt()
                                        )
                                    }
                                    Text(
                                        text = search.rating.toString(),
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }
                                search.types?.take(1)?.forEach { type ->
                                    Text(
                                        text = type,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                                search.city?.let {
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
            Spacer(modifier = Modifier.height(30.dp))
//            Trải nghiệm hàng đầu
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Trải nghiệm hàng đầu", style = MaterialTheme.typography.titleLarge)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    places.placeResult?.data?.forEach { place ->
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
                                    text = "${place.rating}",
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
            Spacer(modifier = Modifier.height(30.dp))
//            Địa điểm được khác du lịch yêu thích
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Địa điểm được khách du lịch yêu thích",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate("nt") }
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Nha Trang")
                    }
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate("dn") }
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Đà Nẵng")
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate("hn") }
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Hà Nội")
                    }
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate("vhl") }
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Vịnh Hạ Long")
                    }
                }
            }
//
        }
    }
}

class SearchFactory(private val query: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchObject::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchObject(query) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}