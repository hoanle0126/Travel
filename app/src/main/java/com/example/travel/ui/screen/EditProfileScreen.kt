package com.example.travel.ui.screen

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.travel.R
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val img: Bitmap = BitmapFactory.decodeResource(
        Resources.getSystem(), android.R.drawable.ic_menu_report_image
    )
    var bitmap by remember {
        mutableStateOf(img)
    }
    val scope = rememberCoroutineScope()




    val launcherImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        val source = it?.let { it1 ->
            ImageDecoder.createSource(context.contentResolver, it1)
        }
        bitmap = source?.let { it1 ->
            ImageDecoder.decodeBitmap(it1)
        }!!
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(start = 10.dp, top = 20.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp),
                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = ""
                    )
                }
                Text(text = "Hồ sơ", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { navController.navigate("edit-profile") }) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.icon_bold_pen),
                        contentDescription = ""
                    )
                }
            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
//            Avatar
            Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.BottomEnd) {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(99.dp))
                        .size(100.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(99.dp)
                        ),
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(99.dp))
                        .clickable { launcherImage.launch("image/*") }
                        .size(30.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(99.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(99.dp)
                        )
                        .zIndex(10f), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_bold_pen),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
//            End Avatar
//            Name
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Tên", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
//
//            Thành phố hiện tại
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Thành phố hiện tại", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
//
//            Trang web
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Trang web", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
//
//            Giới thiệu
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Trang web", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
//
            Button(onClick = {
                             bitmap.let { bitmap ->
                                 uploadImageToFirebase(
                                     bitmap = bitmap,
                                     context = context as ComponentActivity
                                 ){success ->
                                     if(success){
                                         Toast.makeText(
                                             context,
                                             "Success",
                                             Toast.LENGTH_LONG
                                         )
                                             .show()
                                     }
                                 }
                             }
                             }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Lưu", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity, callback: (Boolean) -> Unit){
    val storageRef = Firebase.storage.reference
    val imageRef = storageRef.child("images/test2")

    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)

    val imageData = baos.toByteArray()

    imageRef.putBytes(imageData).addOnSuccessListener {
        callback(true)
    }.addOnFailureListener{
        callback(false)
    }
}