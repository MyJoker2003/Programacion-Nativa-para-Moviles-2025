package com.example.androidcomposetest8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcomposetest8.ui.theme.AndroidComposeTest8Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    private val myviewModel:TimerModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest8Theme {
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
    AndroidComposeTest8Theme {
        AppScreen(TimerModel())
    }
}

@Composable
fun AppScreen(viewModel: TimerModel ,modifier: Modifier = Modifier){
    val corriendo by viewModel.corriendo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #8", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "<< TEMPORARIZADOR >>")
            Spacer(modifier = Modifier.padding(8.dp))
            Row {
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = viewModel.myClock.value.HH,
                    onValueChange = {
                        if(!corriendo){
                            viewModel.updateClock(hours = it)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    readOnly = false,
                    label = { Text("HH") }
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = viewModel.myClock.value.MM,
                    onValueChange = {
                        if(!corriendo){
                            viewModel.updateClock(minutes = it)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    readOnly = false,
                    label = { Text("MM") }
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = viewModel.myClock.value.SS,
                    onValueChange = {
                        if(!corriendo){
                            viewModel.updateClock(seconds = it)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    readOnly = false,
                    label = { Text("SS") }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(onClick = {
                viewModel.iniciarTemporarizador()
            }) { Text("Iniciar") }
            Button(onClick = {viewModel.detenerCronometro()}) { Text(text = "Detener") }
            Button(onClick = {viewModel.reiniciarCronometro()}) { Text(text = "Reiniciar") }
        }
    }
}

data class clock(var HH:String,var MM:String,var SS:String)
class TimerModel: ViewModel(){
    private var _myClock = mutableStateOf(clock("00","00","00"))
    val myClock : State<clock> = _myClock

    private var _tiempoTranscurrido = MutableStateFlow(0L)
    val tiempoTranascurridoObservable: StateFlow<Long> = _tiempoTranscurrido

    private var _corriendo = mutableStateOf(false)
    val corriendo : State<Boolean> = _corriendo

    fun iniciarTemporarizador(){
        if (Validate()) {
            if(!_corriendo.value){
                _corriendo.value = true
                _tiempoTranscurrido.value = toMilliseconds(_myClock.value)
                var reloj = ""
                viewModelScope.launch {
                    while (_corriendo.value && _tiempoTranscurrido.value >0L){
                        _tiempoTranscurrido.value-=1000
                        reloj = formatTime(_tiempoTranscurrido.value)
                        _myClock.value = clock(
                            HH = reloj.split(":")[0],
                            MM = reloj.split(":")[1],
                            SS = reloj.split(":")[2],
                        )
                        delay(1000)
                    }
                }
            }
        }
    }
    fun updateClock(hours: String? = null, minutes: String? = null, seconds: String? = null) {
        _myClock.value = _myClock.value.copy(
            HH = hours ?: _myClock.value.HH,
            MM = minutes ?: _myClock.value.MM,
            SS = seconds ?: _myClock.value.SS
        )
    }

    fun detenerCronometro(){
        _corriendo.value = false
    }

    fun reiniciarCronometro(){
        _myClock.value = clock("00","00","00")
        _tiempoTranscurrido.value = 0L
    }

    override fun onCleared() {
        super.onCleared()
        _corriendo.value = false
    }

    private fun toMilliseconds(setting:clock):Long{
        var convertedMilliseconds = 0L
        convertedMilliseconds += (setting.HH.toLong()*3600000L)
        convertedMilliseconds += (setting.MM.toLong()*60000L)
        convertedMilliseconds += (setting.SS.toLong()*1000L)
        return convertedMilliseconds
    }

    fun formatTime(milliseconds: Long): String {
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / 60000) % 60
        val hours = milliseconds / 3600000
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun Validate():Boolean{
        return true
    }
}