package com.example.travel.ui.screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel.R
import com.example.travel.ui.component.RatingCustom
import com.example.travel.ui.layout.DefaultLayout

@Composable
fun SearchScreen(navController: NavController) {
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
                value = "", onValueChange = {}
            )
//
//            Huy code phần giao diện hiêện mấy cái tìm kiếm ở đây
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
                    repeat(15) {
                        Column(
                            modifier = Modifier
                                .width(180.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .clickable { }
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(0.3f),
                                        RoundedCornerShape(8.dp)
                                    ),
                                contentScale = ContentScale.FillBounds,
                                model = "https://ik.imagekit.io/tvlk/blog/2023/06/chua-thien-mu-1.jpg?tr=dpr-2,w-675",
                                contentDescription = ""
                            )
                            Text(
                                text = "Chùa thiên mụ",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RatingCustom(
                                    value = 3
                                )
                                Text(
                                    text = "3",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            Text(
                                text = "Loại",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "Địa điểm",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
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
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Huế")
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Huế")
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Huế")
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(160.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ){
                        Text(text = "Huế")
                    }
                }
            }
//
        }
    }
}