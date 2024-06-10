package com.example.travel.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel.R
import com.example.travel.data.model.ToDo
import com.example.travel.data.`object`.AuthViewModel
import com.example.travel.ui.component.RatingCustom
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.childEvents
import com.google.firebase.database.database
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(MapboxExperimental::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailToDoScreen(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntry
    val indexToDo = currentBackStackEntry?.arguments?.getInt("toDo")
    val ref = Firebase.database.reference
    val toDoDatabase = ref.child("ToDo")
    var toDoList: List<ToDo> by remember {
        mutableStateOf(emptyList())
    }
    val context = LocalContext.current
    var openEndTimeDialog by remember {
        mutableStateOf(false)
    }
    var openEndDatePickerState = rememberDatePickerState(
        yearRange = IntRange(LocalDate.now().year, 2100),
    )
    var endTime by remember {
        mutableStateOf("")
    }
    var openDesDialog by remember {
        mutableStateOf(false)
    }


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

    if (toDoList.filter { it.email == AuthViewModel().user?.email }.isNotEmpty()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButton = {
                FilledIconButton(onClick = { openDesDialog = true }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
                }
            },
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = ""
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MapboxMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    mapViewportState = MapViewportState().apply {
                        setCameraOptions {
                            zoom(15.0)
                            center(
                                Point.fromLngLat(
                                    toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo!!].place.data.first().longitude!!,
                                    toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo].place.data.first().latitude!!
                                )
                            )
                            pitch(0.0)
                            bearing(0.0)
                        }
                    },
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo!!].name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    if (endTime.isEmpty()) {
                        OutlinedButton(
                            onClick = { openEndTimeDialog = true },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_edit),
                                contentDescription = ""
                            )
                            Text(
                                text = "Thêm ngày cho chuyến đi",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_edit),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(text = endTime, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(0.3f)),
                            contentScale = ContentScale.FillBounds,
                            model = toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo].place.data.first().photos?.first()?.url,
                            contentDescription = ""
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color.Black)
                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo].place.data.first().types?.first()!!,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(
                                text = toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo].place.data.first().name!!,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 2
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RatingCustom(
                                    value = toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo].place.data.first().rating?.toInt()!!
                                )
                                Text(
                                    text = toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo].place.data.first().review_count.toString(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Thông tin chuyến đi",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = toDoList.filter { it.email == AuthViewModel().user?.email }[indexToDo].info,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
//
    if (openEndTimeDialog) {
        DatePickerDialog(
            onDismissRequest = { openEndTimeDialog = false },
            confirmButton = {
                Button(onClick = {
                    endTime = if (openEndDatePickerState.selectedDateMillis != null) {
                        Instant
                            .ofEpochMilli(openEndDatePickerState.selectedDateMillis!!)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    } else {
                        ""
                    }
                    openEndTimeDialog = false
                }) {
                    Text("Test")
                }
            }
        ) {
            DatePicker(openEndDatePickerState)
        }
    }
//    DatePicker
}

@Composable
fun AddDescriptionDialog(onClose: () -> Unit, onAdd: (String) -> Unit) {
    var description by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = onClose) {
        Column(
            modifier = Modifier
                .width(600.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                textStyle = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = { onAdd(description) }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Lưu", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}