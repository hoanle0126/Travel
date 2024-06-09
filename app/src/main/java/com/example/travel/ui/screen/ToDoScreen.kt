package com.example.travel.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel.R
import com.example.travel.data.model.PlaceDetails.PlaceDetails
import com.example.travel.data.model.ToDo
import com.example.travel.data.`object`.AuthViewModel
import com.example.travel.ui.layout.DefaultLayout
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@Composable
fun ToDoScreen(navController: NavController) {
    var name by remember {
        mutableStateOf("")
    }
    val ref = Firebase.database.reference
    val toDoDatabase = ref.child("ToDo")

    var toDoList: List<ToDo> by remember {
        mutableStateOf(emptyList())
    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        toDoDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val toDoListt = mutableListOf<ToDo>()
                for (toDoSnapshot in snapshot.children) {
                    val toDoo = toDoSnapshot.getValue(ToDo::class.java)
                    if (toDoo != null && toDoo.email == AuthViewModel().user?.email) {
                        toDoListt.add(toDoo)
                    }
                }
                toDoList = toDoListt
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

        DefaultLayout(
            navController = navController
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Lập kế hoạch",
                    style = MaterialTheme.typography.displayLarge
                )
                if (toDoList.filter { it.email == AuthViewModel().user?.email }.isEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
//                Lưu địa điểm bạn muốn ghé thăm
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(99.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = R.drawable.icon_bold_heart),
                                contentDescription = ""
                            )
                        }
                        Text(
                            text = "Lưu địa điểm bạn muốn ghé thăm",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
//
//                Xem các danh mục đã lưu trên bản đồ
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(99.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = R.drawable.icon_bold_location),
                                contentDescription = ""
                            )
                        }
                        Text(
                            text = "Xem các danh mục đã lưu trên bản đồ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
//
//                Theo dõi chú thích, liên kết và hơn thế nữa
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(99.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = R.drawable.document_text),
                                contentDescription = ""
                            )
                        }
                        Text(
                            text = "Theo dõi chú thích, liên kết và hơn thế nữa",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
//
//                Chia sẻ và cộng tác trong các kế hoạch của bạn
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(99.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = R.drawable.user_cirlce_add),
                                contentDescription = ""
                            )
                        }
                        Text(
                            text = "Chia sẻ và cộng tác trong các kế hoạch của bạn",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
//
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        toDoList.filter { it.email == AuthViewModel().user?.email }
                            .forEachIndexed { toDoIndex, toDoItem ->
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .fillMaxWidth()
                                        .clickable { navController.navigate("detail-to-do/${toDoIndex}") },
                                ) {
                                    AsyncImage(
                                        model = toDoItem.place.data.first().photos?.first()?.url,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(MaterialTheme.colorScheme.primary.copy(0.3f)),
                                        contentScale = ContentScale.FillBounds
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = toDoItem.name,
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
