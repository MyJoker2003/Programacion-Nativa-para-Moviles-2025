package com.example.androidcomposetest4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.androidcomposetest4.ui.theme.AndroidComposeTest4Theme

//Aplicacion Calculadora Basica
class MainActivity : ComponentActivity() {
    private val operaModel:OperationModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposeTest4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppScreen(
                        operaModel = operaModel,
                        modifier = Modifier.padding(innerPadding)
                       )
                }
            }
        }
    }
}

@Composable
fun AppScreen(operaModel:OperationModel,modifier: Modifier = Modifier){
    val options = listOf(
        Option(1,"Sumar"),
        Option(2,"Restar"),
        Option(3,"Multiplicar"),
        Option(4,"Dividir")
    )
    //val selectedOption by operaModel.operationIndex
    //var selectedOption by operaModel.operationIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #4", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = operaModel.valor1.value,
                onValueChange = {newValue -> operaModel.valor1.value = newValue},
                label = { Text(text="Ingresa el 1er Valor") },
                modifier= Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.padding(4.dp))
            OutlinedTextField(
                value = operaModel.valor2.value,
                onValueChange = {newValue -> operaModel.valor2.value = newValue},
                label = { Text(text="Ingresa el 2do Valor") },
                modifier= Modifier.fillMaxWidth(0.7f)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Selecciona una Operacion")
            options.forEach { option->
                Row (
                    Modifier.padding(1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option.value == operaModel.operationIndex.value),
                        onClick = {operaModel.updateOperationIndex(option.value)}
                    )
                    Text(text = option.name, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Button(onClick = {operaModel.Operate()}) {
                Text(text = "Calcular")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "RESULTADO")
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = operaModel.result.value)

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
    AndroidComposeTest4Theme {
        AppScreen(OperationModel())
    }
}

data class Option(val value:Int,val name:String)

class OperationModel: ViewModel(){
    var operationIndex = mutableIntStateOf(0)
    var valor1 =  mutableStateOf("")
    var valor2 =  mutableStateOf("")
    var result =  mutableStateOf("")

    fun Operate(){
        if (Validate()){
            val num1 = this.valor1.value.toDoubleOrNull()
            val num2 = this.valor2.value.toDoubleOrNull()
            when(operationIndex.value){
                1->Sumar(num1!!,num2!!)
                2->Restar(num1!!,num2!!)
                3->Multiplicar(num1!!,num2!!)
                4->{}

            }
        }
    }

    fun updateOperationIndex(newIndex:Int){
        operationIndex.intValue = newIndex
    }
    fun Sumar(Number1:Double,Number2: Double){
        result.value="${Number1+Number2}"
    }
    fun Restar(Number1:Double,Number2: Double){
        result.value="${Number1-Number2}"
    }

    fun Multiplicar(Number1:Double,Number2: Double) {
        result.value = "${Number1 * Number2}"
    }

    fun Dividir(Number1:Double,Number2: Double) {
        if (Number2 == 0.0) result.value="No se puede Dividir entre 0"
        else result.value = "${Number1 / Number2}"
    }

    fun Validate():Boolean{
        result.value = ""
        var resultado = true;
        if (valor1.value  == "" || valor2.value == "") {
            result.value += "Debes llenar los dos Campos\n"
            resultado=false
        }else{
            val num1 : Double? = valor2.value.toDoubleOrNull()
            val num2 : Double? = valor1.value.toDoubleOrNull()
            if (num1 == null || num2 == null) {
                resultado = false
                result.value += "Ingresa Valores Numericos\n"
            }
        }
        return resultado
    }
}