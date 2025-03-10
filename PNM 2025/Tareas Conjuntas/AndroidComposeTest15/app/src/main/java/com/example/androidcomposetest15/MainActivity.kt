package com.example.androidcomposetest15

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.androidcomposetest15.ui.theme.AndroidComposeTest15Theme

class MainActivity : ComponentActivity() {
    val myviewModel: volumeModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposeTest15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppScreen(
                        viewModel = myviewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidComposeTest15Theme {
        AppScreen(volumeModel())
    }
}

@Composable
fun AppScreen(viewModel: volumeModel,modifier: Modifier=Modifier){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #15", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "<< SUBIR/BAJAR VOL. >>", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(18.dp))
            Button(onClick = { viewModel.subirVolumen(context) }
            ) {
                Text(text = "Subir", fontSize = 29.sp)
            }
            Spacer(modifier = Modifier.padding(20.dp))

            Button(onClick = { viewModel.bajarVolumen(context) }
            ) {
                Text(text = "Bajar", fontSize = 29.sp)
            }
        }
    }
}

class volumeModel:ViewModel(){
    fun subirVolumen(context:Context){
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI)
    }
    fun bajarVolumen(context: Context){
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI)
    }
}