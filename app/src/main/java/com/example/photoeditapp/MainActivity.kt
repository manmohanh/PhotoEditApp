package com.example.photoeditapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.photoeditapp.ui.theme.PhotoEditAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoEditAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SelectImage()
                }
            }
        }
    }
}

@Composable
fun SelectImage() {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            imageUri.value = it
            if (it != null) {
                handleLaunchActivity(context,it)
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select an image",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                  imagePicker.launch("image/*")
                },
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            ) {
                Text(text = "Picker")
            }
            Button(
                onClick = { },
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            ) {
                Text(text = "Camera")
            }
        }
    }
}

fun handleLaunchActivity(context: Context, it: Uri) {
    val intent = Intent(context,EditActivity::class.java)
    intent.putExtra("imageUri",it.toString())
    context.startActivity(intent)
}

