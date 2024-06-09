package com.example.travel.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travel.R
import com.example.travel.data.model.ListNavigationModel

@Composable
fun DefaultLayout(
    navController: NavController = rememberNavController(),
    contentSpacing: Dp = 0.dp,
    topAppBar: Boolean = false,
    contentPadding: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    val currentRoute = navController.currentDestination?.route.toString()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            if(topAppBar){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
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
                .padding(contentPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(contentSpacing)
        ) {
            content()
        }
    }
}