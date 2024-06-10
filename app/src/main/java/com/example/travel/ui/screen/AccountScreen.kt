package com.example.travel.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel.R
import com.example.travel.data.controller.AuthController
import com.example.travel.data.model.ListNavigationModel
import com.example.travel.data.`object`.AuthViewModel

@Composable
fun AccountScreen(navController: NavController) {
    val currentRoute = navController.currentDestination?.route.toString()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        ambientColor = Color.Black,
                        spotColor = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                    .background(
                        Color.White
                    )
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ListNavigationModel().getAll().forEach {
                    Column(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(it.route)
                            }
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(it.name.length.times(6.dp))
                                .height(3.dp)
                                .background(
                                    if (currentRoute == it.route) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    RoundedCornerShape(
                                        bottomEnd = 99.dp,
                                        bottomStart = 99.dp
                                    )
                                )
                        )
                        Icon(
                            painter = painterResource(id = if (currentRoute == it.route) it.iconBold else it.icon),
                            contentDescription = "",
                            tint = if (currentRoute == it.route)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (currentRoute == it.route)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(text = "Tài khoản", style = MaterialTheme.typography.displayLarge)
                AsyncImage(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(0.3f),
                            RoundedCornerShape(99.dp)
                        ),
                    model = AuthViewModel().user?.photoUrl,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }
//           Hồ sơ
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .clickable { navController.navigate("profile") }
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_bold_profile),
                        contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Hồ sơ",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowRight,
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.outline)
                )
            }
//
            Spacer(modifier = Modifier.weight(1f))
//            Đăng xuất
            Button(
                onClick = { AuthController(context, navController).signOut() },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(text = "Đăng xuất", style = MaterialTheme.typography.titleMedium)
            }
//
        }
    }
}