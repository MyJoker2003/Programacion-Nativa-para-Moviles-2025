package com.example.androidcomposetest7

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcomposetest7.ui.theme.AndroidComposeTest7Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val myviewModel:TimerModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest7Theme {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidComposeTest7Theme {
        AppScreen(TimerModel())
    }
}

@Composable
fun AppScreen(viewModel: TimerModel,modifier: Modifier=Modifier){
    val tiempoTranscurrido = viewModel.tiempoTranascurridoObservable.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #7", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "<< CRONOMETRO >>")
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = formatTime(tiempoTranscurrido),
                fontSize = 70.sp
            )
            Spacer(modifier = Modifier.padding(27.dp))
            Button(onClick = {viewModel.iniciarCronometro()}) { Text("Iniciar") }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {viewModel.detenerCronometro()}) { Text(text = "Detener") }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {viewModel.reiniciarCronometro()}) { Text(text = "Reiniciar") }

            //Text(text = tester.value)
        }
    }
}

fun formatTime(milliseconds: Long): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / 60000) % 60
    val hours = milliseconds / 3600000
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}


data class clock(var HH:String,var MM:String,var SS:String)

class TimerModel():ViewModel(){
    private var _myClock = mutableStateOf(clock("00","00","00"))
    val myClock : State<clock> = _myClock
    private var running = mutableStateOf(false)

    private var _tiempoTranscurrido = MutableStateFlow(0L)
    val tiempoTranascurridoObservable: StateFlow<Long> = _tiempoTranscurrido

    private var corriendo = false

    fun iniciarCronometro(){

        if(!corriendo){
            corriendo = true
            viewModelScope.launch {
                while (corriendo){
                    _tiempoTranscurrido.value+=1000
                    delay(1000)
                }
            }
        }
    }

    fun detenerCronometro(){
        corriendo = false
    }

    fun reiniciarCronometro(){
        _tiempoTranscurrido.value = 0L
    }

    override fun onCleared() {
        super.onCleared()
        corriendo = false
    }
}