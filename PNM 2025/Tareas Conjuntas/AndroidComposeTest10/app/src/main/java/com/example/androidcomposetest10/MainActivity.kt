package com.example.androidcomposetest10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.androidcomposetest10.ui.theme.AndroidComposeTest10Theme

// App que despliega las tablas de multiplicar de un numero dado
class MainActivity : ComponentActivity() {
    private val myviewModel:AppModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest10Theme {
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
    AndroidComposeTest10Theme {
        AppScreen(AppModel())
    }
}

@Composable
fun AppScreen(viewModel: AppModel,modifier: Modifier=Modifier){
    val resultList by viewModel.resultContent
    val display by viewModel.display

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #10", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = viewModel.baseNumber.value,
                onValueChange = { viewModel.updateBaseNumber(it) },
                label = { Text("Ingresa un Numero...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                viewModel.generateTable()
            }) {
                Text(text = "Mostrar")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "RESULTADO")
            Spacer(modifier = Modifier.padding(15.dp))

            if (display){
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .background(Color.Yellow)
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp,Color.Black).background(Color.Green),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Operacion")
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp,Color.Black).background(Color.Green),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Resultado")
                        }
                    }
                    resultList.forEach { element ->
                        Row {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .border(1.dp,Color.Black),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = element.operacion)
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .border(1.dp,Color.Black),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = element.resultado)
                            }
                        }
                    }
                }

            }

        }
    }
}

data class multiplo(val operacion:String,val resultado:String)
class AppModel:ViewModel(){
    private var _resultContent = mutableStateListOf<multiplo>()
    val resultContent: State<List<multiplo>> = derivedStateOf { _resultContent }

    private var _display = mutableStateOf(false)
    val display: State<Boolean> = _display

    private var _baseNumber = mutableStateOf("")
    val baseNumber:State<String> = _baseNumber

    fun updateBaseNumber(value: String){
        _baseNumber.value = value
    }

    private fun addMultiplo(newMultiplo: multiplo) {
        _resultContent.add(newMultiplo)
    }

    fun generateTable(){
        if (Validate()) {
            _display.value = false
            _resultContent.clear()
            var operationBody=""
            var resultado = ""
            for (i in  1..10){
                operationBody = "${_baseNumber.value} x $i"
                resultado = (baseNumber.value.toInt()*i).toString()
                addMultiplo(multiplo(operationBody,resultado))
            }
            _display.value = true
        }
    }
    
    private fun Validate():Boolean{
        var flag = true;
        if (_baseNumber.value.toIntOrNull() == null) flag = false
        return flag;
    }
}