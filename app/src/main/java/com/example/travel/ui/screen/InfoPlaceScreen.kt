package com.example.travel.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.travel.R
import com.example.travel.data.model.PlaceDetails.Photo
import com.example.travel.data.`object`.AuthViewModel
import com.example.travel.data.`object`.DetailsObject
import com.example.travel.data.`object`.NearbyObject
import com.example.travel.data.`object`.NearbyRestaurantObject
import com.example.travel.data.`object`.ReviewsObject
import com.example.travel.ui.component.RatingCustom
import com.example.travel.ui.layout.DefaultLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Composable
fun InfoPlaceScreen(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntry
    val factory = DetailsFactory(
        currentBackStackEntry?.arguments?.getString("id").toString()
    )
    val factory2 = NearbyFactory(
        currentBackStackEntry?.arguments?.getString("lat").toString(),
        currentBackStackEntry?.arguments?.getString("lng").toString()
    )
    val factory3 = NearbyResFactory(
        currentBackStackEntry?.arguments?.getString("lat").toString(),
        currentBackStackEntry?.arguments?.getString("lng").toString()
    )
    val factory4 = ReviewsFactory(
        currentBackStackEntry?.arguments?.getString("id").toString(),
    )
    val details: DetailsObject = viewModel(factory = factory)
    val nearby: NearbyObject = viewModel(factory = factory2)
    val nearbyres: NearbyRestaurantObject = viewModel(factory = factory3)
    val reviews: ReviewsObject = viewModel(factory = factory4)
    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://travel-f4cbd-default-rtdb.asia-southeast1.firebasedatabase.app")
    val databaseReference: DatabaseReference = database.reference.child("Places")
    val authViewModel: AuthViewModel = viewModel()
    val currentUserEmail = authViewModel.user?.email

    DefaultLayout(
        navController = navController,
        contentSpacing = 20.dp,
        topAppBar = true
    ) {
//        Carousel
        details.detailsResult?.data?.forEach { details ->
            LazyRow{
                item {
                    details.photos?.forEach{ photo ->
                        AsyncImage(
                            model = photo.url,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .background(MaterialTheme.colorScheme.primary.copy(0.3f)),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                }
            }
        }
//        End carousel
//
        details.detailsResult?.data?.forEach{ details ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
    //            Title
                details.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.displayMedium
                    )
                }
    //            End title
    //            Review
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    details.rating?.let {
                        RatingCustom(
                            value = it.toInt(),
                            size = 1.5f
                        )
                    }
                    Text(
                        text = "${details.review_count} Đánh giá",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer.copy(0.3f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(
                                vertical = 2.dp,
                                horizontal = 6.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.secondary,
                            imageVector = Icons.Rounded.Star, contentDescription = ""
                        )
                        Text(
                            text = "${details.rating}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
    //            End review
            }
        }
//
//        Action button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.icon_outlined_bookmark),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Đặt lịch",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_bold_send),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Địa chỉ",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            IconButton(
                modifier = Modifier
                    .height(56.dp), // Set to match button height
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_outlined_heart),
                    contentDescription = ""
                )
            }
        }
//
//        Giờ làm việc
        details.detailsResult?.data?.forEach { details ->
            Row(
                modifier = Modifier
                    .clickable { }
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Thời gian hoạt động",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = formatWorkingHours(details.working_hours.toString()),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
//
//        Khách sạn lân cận
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Khách sạn lân cận",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {nearby.nearbyResult?.data?.forEach{ nearby ->
                Row(
                    modifier = Modifier
                        .height(80.dp)
                        .clickable {
                            navController.navigate("details/${nearby.business_id}/${nearby.latitude.toString()}/${nearby.longitude.toString()}")
                            val placeUpdates = mapOf(
                                "business_id" to nearby.business_id,
                                "name" to nearby.name,
                                "rating" to nearby.rating,
                                "type" to nearby.types
                                    .firstOrNull()
                                    .orEmpty(),
                                "city" to nearby.city,
                                "photo" to nearby.photos.firstOrNull()?.src.orEmpty(),
                                "ltn" to nearby.latitude.toString(),
                                "lng" to nearby.longitude.toString(),
                                "user" to currentUserEmail
                            )
                            databaseReference
                                .child(nearby.business_id)
                                .setValue(placeUpdates)
                        },
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    nearby.photos?.take(1)?.forEach { photo ->
                        AsyncImage(
                            model = photo.src,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .size(80.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(0.3f),
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 4.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = nearby.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RatingCustom(
                                value = nearby.rating.toInt()
                            )
                            Text(
                                text = "${nearby.review_count}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${nearby.city}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            }
        }
//
//        Nhà hàng lận cận
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nhà hàng lận cận",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                nearbyres.nearbyResult?.data?.forEach{ nearbyres ->
                    Row(
                        modifier = Modifier
                            .height(80.dp)
                            .clickable {
                                navController.navigate("details/${nearbyres.business_id}/${nearbyres.latitude.toString()}/${nearbyres.longitude.toString()}")
                                val placeUpdates = mapOf(
                                    "business_id" to nearbyres.business_id,
                                    "name" to nearbyres.name,
                                    "rating" to nearbyres.rating,
                                    "type" to nearbyres.types
                                        .firstOrNull()
                                        .orEmpty(),
                                    "city" to nearbyres.city,
                                    "photo" to nearbyres.photos.firstOrNull()?.src.orEmpty(),
                                    "ltn" to nearbyres.latitude.toString(),
                                    "lng" to nearbyres.longitude.toString(),
                                    "user" to currentUserEmail
                                )
                                databaseReference
                                    .child(nearbyres.business_id)
                                    .setValue(placeUpdates)
                            },
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        nearbyres.photos?.take(1)?.forEach { photo ->
                            AsyncImage(
                                model = photo.src,
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .size(80.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(0.3f),
                                        RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(vertical = 4.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = nearbyres.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                RatingCustom(
                                    value = nearbyres.rating.toInt()
                                )
                                Text(
                                    text = "${nearbyres.review_count}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${nearbyres.city}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
//        Đánh giá
        Tabs(details = details, reviews = reviews)
//
        Spacer(modifier = Modifier.height(20.dp))
    }
}

val images = listOf(
    "https://statics.vinpearl.com/ch%C3%B9a%20thi%C3%AAn%20m%E1%BB%A5%201_1690188137.jpg",
    "https://ik.imagekit.io/tvlk/blog/2023/06/chua-thien-mu-1.jpg?tr=dpr-2,w-675",
    "https://statics.vinpearl.com/Chua-Thien-Mu-Hue-2_1690873361.jpg",
    "https://file.baothuathienhue.vn/data2/image/news/2021/20211104/origin/5091635992794.jpeg",
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Carousel(images: List<Photo>?) {
    val pagerState = rememberPagerState()

    images?.let {
        HorizontalPager(
        state = pagerState,
        count = it.size,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) { page ->
        Image(
            painter = rememberAsyncImagePainter(images[page].url), contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
    }
}

@Composable
fun Tabs(details: DetailsObject?, reviews: ReviewsObject?) {
    var tabSelected by remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
//        Tab row
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
//            Tab row 1
            Column(
                modifier = Modifier
                    .clickable { tabSelected = 0 }
                    .height(36.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Đánh giá",
                        style = if (tabSelected == 0) MaterialTheme.typography.titleSmall
                        else MaterialTheme.typography.bodySmall
                    )
                }
                Box(
                    modifier = Modifier
                        .width(68.dp)
                        .height(3.dp)
                        .background(
                            if (tabSelected == 0) MaterialTheme.colorScheme.onSurface
                            else Color.Transparent
                        )
                )
            }
//
//            Tab row 2
//            Column(
//                modifier = Modifier
//                    .clickable { tabSelected = 1 }
//                    .height(36.dp),
//                verticalArrangement = Arrangement.SpaceBetween,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier
//                        .weight(1f),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "H&Đ",
//                        style = if (tabSelected == 1) MaterialTheme.typography.titleSmall
//                        else MaterialTheme.typography.bodySmall
//                    )
//                }
//                Box(
//                    modifier = Modifier
//                        .width(40.dp)
//                        .height(3.dp)
//                        .background(
//                            if (tabSelected == 1) MaterialTheme.colorScheme.onSurface
//                            else Color.Transparent
//                        )
//                )
//            }
//
        }
//
//        Tab content 1
        if (tabSelected == 0) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                OutlinedTextField
//                OutlinedTextField(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    value = "",
//                    onValueChange = {},
//                    leadingIcon = {
//                        Icon(
//                            painter = painterResource(id = R.drawable.icon_outlined_search),
//                            contentDescription = ""
//                        )
//                    },
//                    textStyle = MaterialTheme.typography.bodyMedium,
//                    shape = RoundedCornerShape(99.dp),
//                    label = {
//                        Text(
//                            text = "Tìm kiếm đánh giá",
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    },
//                    colors = OutlinedTextFieldDefaults.colors(
//
//                    ),
                //)
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(2.dp)
//                        .background(
//                            MaterialTheme.colorScheme.outline.copy(0.6f)
//                        )
//                )
//                Thống kê đánh giá
                if (details != null) {
                    details.detailsResult?.data?.forEach { details ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            details.rating?.let {
                                RatingCustom(
                                    value = it.toInt(),
                                    size = 1.5f,
                                    modifier = Modifier
                                        .width(140.dp)
                                )
                            }
                            Text(text = "${details.review_count} đã đánh giá", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .width(140.dp),
                                text = "Xuất sắc",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            LinearProgressIndicator(
                                progress = 0.6f,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(99.dp))
                            )
                        }
                    }
                }
//
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            MaterialTheme.colorScheme.outline.copy(0.6f)
                        )
                )
//                Danh sách đánh giá
                if (reviews != null) {
                    reviews.reviewsResult?.data?.reviews?.forEach{ reviews ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                    AsyncImage(
                                        model = reviews.user_avatar,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(52.dp)
                                            .background(
                                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                                RoundedCornerShape(99.dp)
                                            )
                                    )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 4.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    reviews.user_name?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    reviews.review_time?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                            reviews.review_rate?.let {
                                RatingCustom(
                                    value = it.toInt(),
                                    size = 1.5f
                                )
                            }
                            reviews.review_text?.let {
                                Text(
                                    style = MaterialTheme.typography.bodyMedium,
                                    text = it
                                )
                            }
                            reviews.review_photos?.take(3)?.forEach{ photo ->
                                AsyncImage(
                                    model = photo,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .background(
                                            MaterialTheme.colorScheme.outline.copy(0.6f)
                                        )
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(
                                    MaterialTheme.colorScheme.outline.copy(0.6f)
                                )
                        )
                    }
                }
//
            }
        }
    }
}
class DetailsFactory(private val id: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsObject::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsObject(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NearbyFactory(private val lat: String, private val lng: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NearbyObject::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NearbyObject(lat, lng) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class NearbyResFactory(private val lat: String, private val lng: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NearbyRestaurantObject::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NearbyRestaurantObject(lat, lng) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class ReviewsFactory(private val id: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsObject::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewsObject(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
fun formatWorkingHours(hours: String?): String {
    if (hours.isNullOrEmpty()) return "Không có dữ liệu"
    var formattedHours = hours
        .replace("{", "")
        .replace("}", "")
        .replace("[]", "Không có dữ liệu")
        .replace("PM,", "PM |")
        .replace("=[", ":")
        .replace("]", "")
        .replace(",", "\n")
    return formattedHours
}
