package com.example.androidcomposetest1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcomposetest1.ui.theme.AndroidComposeTest1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyColorChangingScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyColorChangingScreen(modifier: Modifier =Modifier){
    val colorsList = remember { mutableStateListOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta
    ) }
    var CurrentColorIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #1")
        Spacer(modifier=Modifier.padding(10.dp))
        Column(
            modifier = Modifier.fillMaxSize().background(colorsList[CurrentColorIndex]),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Push to Change Color:")
            Spacer(modifier = Modifier.padding(6.dp))
            Button(onClick = {
                CurrentColorIndex = (CurrentColorIndex+1) % colorsList.size
            }) {
                Text(text="PUSH!!")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidComposeTest1Theme {
        MyColorChangingScreen()
    }
}