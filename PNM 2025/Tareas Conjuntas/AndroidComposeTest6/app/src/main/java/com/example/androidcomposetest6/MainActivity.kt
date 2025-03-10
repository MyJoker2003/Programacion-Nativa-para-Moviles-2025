package com.example.androidcomposetest6

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.androidcomposetest6.ui.theme.AndroidComposeTest6Theme

//App para Calculo del Indice de Masa Corporal IMC
class MainActivity : ComponentActivity() {
    val myviewModel: IMCModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposeTest6Theme {
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
    AndroidComposeTest6Theme {
        AppScreen(IMCModel())
    }
}

@Composable
fun AppScreen(viewModel: IMCModel,modifier: Modifier=Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #6", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "<< Calculate IMC >>")
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = viewModel.weight.value,
                onValueChange = {viewModel.setWeight(it)},
                label = { Text(text = "Ingresa tu peso (kg)") },
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = viewModel.altura.value,
                onValueChange = {viewModel.setAltura(it)},
                label = { Text(text = "Ingresa tu estatura (m)") },
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Button(onClick = {viewModel.calculate()}) {
                Text(text = "Calcular")
            }
            Spacer(modifier = Modifier.padding(18.dp))
            Text(text = "RESULTADO", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Text(text = viewModel.result.value)
        }
    }
}

class IMCModel: ViewModel(){
    private var _weight = mutableStateOf("")
    val weight : State<String> = _weight
    private var _altura = mutableStateOf("")
    val altura : State<String> = _altura
    private var _result = mutableStateOf("")
    val result : State<String> = _result

    fun setWeight(value: String){
        this._weight.value = value
    }

    fun setAltura(value: String){
        this._altura.value = value
    }

    private fun CalculateIMC():Double{
        val peso = weight.value.toDouble()
        val alt = altura.value.toDouble()
        return (peso/(alt*alt))
    }

    fun calculate(){
        if (Validate()) {
            _result.value = CalculateIMC().toString()
        } else _result.value="Hubo un Error"
    }

    private fun Validate(): Boolean{
        _result.value=""
        var flag = true

        if(altura.value =="" || weight.value =="") flag =  false
        else _result.value+="Llena todos los campos"

        if (altura.value.toDoubleOrNull() == null || weight.value.toDoubleOrNull() == null) flag = false
        else _result.value+="Solo se permiten valores numericos"

        return flag
    }
}