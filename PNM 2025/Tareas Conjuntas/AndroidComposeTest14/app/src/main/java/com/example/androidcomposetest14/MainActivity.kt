package com.example.androidcomposetest14

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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.androidcomposetest14.ui.theme.AndroidComposeTest14Theme
import kotlin.math.sqrt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    val myviewModel: EcuationSolverModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest14Theme {
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
    AndroidComposeTest14Theme {
        AppScreen(EcuationSolverModel())
    }
}

@Composable
fun AppScreen(viewModel: EcuationSolverModel,modifier: Modifier=Modifier){
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var c by remember { mutableStateOf("") }
    val solution1 by viewModel.solution1
    val solution2 by viewModel.solution2

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #14", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "<< ECUATION SOLVER (CUADRATIC) >>", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(
                value = a,
                onValueChange = {a=it},
                label = { Text("Coeficiente a") }
            )
            Spacer(modifier=Modifier.padding(8.dp))
            TextField(
                value = b,
                onValueChange = {b=it},
                label = { Text("Coeficiente b") }
            )
            Spacer(modifier=Modifier.padding(8.dp))

            TextField(
                value = c,
                onValueChange = {c=it},
                label = { Text("Coeficiente c") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                val aVal = a.toDoubleOrNull()?: return@Button
                val bVal = b.toDoubleOrNull()?: return@Button
                val cVal = c.toDoubleOrNull()?: return@Button
                viewModel.solve(aVal, bVal ,cVal)
            }) {
                Text(text = "SOLVE")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "RESULTADO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(14.dp))
            Text(text = viewModel.resultado.value)
        }
    }
}

class EcuationSolverModel:ViewModel(){
    private var _solution1 =  mutableStateOf<Double?>(null)
    val solution1 : State<Double?> = _solution1

    private var _solution2 =  mutableStateOf<Double?>(null)
    val solution2 : State<Double?> = _solution2

    private var _resultado = mutableStateOf("")
    val resultado : State<String> = _resultado

    fun solve(a:Double,b:Double,c:Double){
        val discriminante = b * b - 4 * a * c
        when{
            discriminante > 0 ->{
                _solution1.value = (-b + sqrt(discriminante))/ (2*a)
                _solution2.value = (-b - sqrt(discriminante))/ (2*a)
            }
            discriminante == 0.0 ->{
                _solution1.value = -b / (2*a)
                _solution2.value = null
            }
            else->{
                _solution1.value = null
                _solution2.value = null
            }
        }
        _resultado.value = "Solution 1: ${solution1.value} \n Solution 2: ${solution2.value}"
    }
}