package com.example.androidcomposetest9

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import com.example.androidcomposetest9.ui.theme.AndroidComposeTest9Theme
import androidx.compose.ui.text.input.KeyboardType

//Calculadora de Propinas
class MainActivity : ComponentActivity() {
    private val myviewModel:PropinaModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest9Theme {
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
    AndroidComposeTest9Theme {
        AppScreen(PropinaModel())
    }
}

@Composable
fun AppScreen(viewModel: PropinaModel,modifier: Modifier =Modifier){
    var total = viewModel.montoOriginal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #9", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                value = total.value,
                onValueChange = {viewModel.updateMontoOriginal(it)},
                label = { Text("Monto Original") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Elige el Porcentaje (%) de la Propina")
            Spacer(modifier = Modifier.padding(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = viewModel.ratioSelected.value == "10",
                    onClick = { viewModel.updateRatioSelected("10") }
                )
                Text(text = "10 %")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = viewModel.ratioSelected.value == "15",
                    onClick = { viewModel.updateRatioSelected("15") }
                )
                Text(text = "15 %")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = viewModel.ratioSelected.value == "20",
                    onClick = { viewModel.updateRatioSelected("20") }
                )
                Text(text = "20 %")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = viewModel.ratioSelected.value == "null",
                    onClick = { viewModel.updateRatioSelected("null") }
                )
                Text(text = "Otro")
            }

            if(viewModel.ratioSelected.value == "null"){
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = viewModel.forOtherSelected.value,
                    onValueChange = { viewModel.updateforOtherSelected(it) },
                    label = { Text("Porcentaje") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Button(onClick = {viewModel.CaculateTotal()}) {
                Text(text = "Calcular")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "RESULTADO", fontWeight = FontWeight.Bold)
            Text(text = viewModel.montoTotal.value)

        }
    }
}

class PropinaModel:ViewModel(){

    private var _ratioSelected = mutableStateOf("")
    val ratioSelected:State<String> = _ratioSelected

    private var _montoOriginal = mutableStateOf("")
    val montoOriginal:State<String> = _montoOriginal

    private var _montoTotal = mutableStateOf("")
    val montoTotal:State<String> = _montoTotal

    private var _forOtherSelected = mutableStateOf("")
    val forOtherSelected :State<String> = _forOtherSelected

    private fun Validate(): Boolean{
        _montoTotal.value = ""
        var flag = true
        if (montoOriginal.value.toDoubleOrNull() == null) {
            flag = false
            _montoTotal.value+="*Ingresa el monto a pagar"
        }
        if (ratioSelected.value == "") {
            flag = false
            _montoTotal.value+="*Selecciona un porcentaje (%)"
        }
        if (ratioSelected.value == "null" && forOtherSelected.value.toDoubleOrNull() == null) {
            flag = false
            _montoTotal.value+="*Ingresa el porcentaje deseado"
        }
        return flag
    }

    fun updateMontoOriginal(value:String){
        _montoOriginal.value = value
    }
    fun updateRatioSelected(value: String) {
        _ratioSelected.value = value
    }
    fun updateforOtherSelected(value: String){
        _forOtherSelected.value = value
    }

    fun CaculateTotal(){
        if (Validate()){
            val total = _montoOriginal.value.toDouble() + calculatePropina()
            _montoTotal.value = total.toString()
        }
    }

    private fun calculatePropina():Double{
        var montoPropina = 0.0
        when(_ratioSelected.value){
            "10"->{montoPropina = _montoOriginal.value.toDouble()*0.10}
            "15"->{montoPropina = _montoOriginal.value.toDouble()*0.15}
            "20"->{montoPropina = _montoOriginal.value.toDouble()*0.20}
            else->{
                montoPropina = _montoOriginal.value.toDouble()*(_forOtherSelected.value.toDouble()/100.0)
            }
        }
        return montoPropina
    }
}