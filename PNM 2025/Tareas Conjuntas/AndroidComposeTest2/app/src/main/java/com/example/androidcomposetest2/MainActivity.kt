package com.example.androidcomposetest2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcomposetest2.ui.theme.AndroidComposeTest2Theme

class MainActivity : ComponentActivity() {
    private val myGameModel: GameModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyScreen(
                        myGameModel = myGameModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyScreen(myGameModel: GameModel, modifier: Modifier=Modifier){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #2")
        Spacer(modifier=Modifier.padding(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ingresa un Entero entre 1 y 100")
            Spacer(modifier=Modifier.padding(8.dp))
            TextField(
                value = myGameModel.number.value,
                onValueChange = { newText ->
                    myGameModel.number.value = newText
                },
                label = { Text("Ingresa un numero...") }
            )
            Spacer(modifier = Modifier.padding(13.dp))
            Button(onClick = {
                myGameModel.Adivina()
            }) {
                Text(text = "Try")
            }
            Spacer(modifier=Modifier.padding(16.dp))
            Text(text = "Resultado:")
            Spacer(modifier=Modifier.padding(16.dp))
            Text(text = myGameModel.result.value)
            Spacer(modifier = Modifier.padding(20.dp))
            if (myGameModel.playFinished.value){
                Button(onClick = {
                    myGameModel.regenerateRandomNum()
                }) {
                    Text(text = "Restart")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidComposeTest2Theme {
        MyScreen(GameModel())
    }
}