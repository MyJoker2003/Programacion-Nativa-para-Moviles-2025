package com.example.androidcomposetest5

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.androidcomposetest5.ui.theme.AndroidComposeTest5Theme

//App de Conversion de Unidades "Edicion Temeperatura"
class MainActivity : ComponentActivity() {
    val myViewModel:ConvertionModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposeTest5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppScreen(
                        viewModel = myViewModel,
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
    AndroidComposeTest5Theme {
        AppScreen(ConvertionModel())
    }
}

@Composable
fun AppScreen(viewModel: ConvertionModel,modifier: Modifier =Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #5", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Conversor de Unidades\n << Temperature Edition>>")
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = viewModel.valor1.value,
                onValueChange = {viewModel.valor1.value = it},
                label = { Text(text = "Ingresa el valor a convetir") },

            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text="De:")
            DropdownMenuBox(
                label = "Seleccione una opcion",
                options = viewModel.medidasDisponibles1,
                selectedOption = viewModel.medidaSeleccionada1.value,
                onOptionSelected = {viewModel.setOringinalM(it)}
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Convertir a:")
            DropdownMenuBox(
                label = "Seleccione una opcion",
                options = viewModel.medidasDisponibles2,
                selectedOption = viewModel.medidaSeleccionada2.value,
                onOptionSelected = { viewModel.setConvertedM(it) }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Button(onClick = {viewModel.Convert()}) {
                Text(text = "Convertir")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "RESULTADO:", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text(text = viewModel.result.value)
        }
    }
}

class ConvertionModel():ViewModel(){
    private val _options = listOf("Grados Celcius", "Grados Fahrenheit","Grados Kelvin")
    var medidaSeleccionada1 = mutableStateOf<String?>(null)
        private set
    var medidaSeleccionada2 = mutableStateOf<String?>(null)
        private set

    var valor1 = mutableStateOf("")
    var result = mutableStateOf("")


    val medidasDisponibles1: List<String> get()= _options.filter { it != medidaSeleccionada2.value }
    val medidasDisponibles2: List<String> get()= _options.filter { it != medidaSeleccionada1.value }

    fun setOringinalM(eleccion: String?){ this.medidaSeleccionada1.value = eleccion }

    fun setConvertedM(eleccion: String?){ this.medidaSeleccionada2.value = eleccion }

    fun getOriginalM(): String?{ return this.medidaSeleccionada1.value}
    fun getConverrtedM(): String?{ return this.medidaSeleccionada2.value}

    fun Validate() :Boolean {
        var resultado = true
        if (this.valor1.value.equals("") || this.valor1.value.equals(null)){
            resultado = false
            result.value = "Debes ingresar el valor a convertir."
        }else{
            if (this.valor1.value.toDoubleOrNull() == null){
                resultado = false;
                result.value="Solo se permiten valores numericos"
            }
        }
        return resultado
    }

    fun Convert(){
        when(medidaSeleccionada2.value){
            "Grados Celcius"->{result.value = ToCelsius(this.medidaSeleccionada1.value!!).toString()}
            "Grados Fahrenheit"->{ToFahrenheit(this.medidaSeleccionada1.value!!)}
            "Grados Kelvin"->{ToKelvin(this.medidaSeleccionada1.value!!)}
        }
    }

    fun ToFahrenheit(selected:String): Double{
        if (this.medidaSeleccionada1.value.equals("Grados Celcius")) {
            return (9/5*valor1.value.toDouble())-32.0
        }
        else return ToCelsius("Grados Kelvin")*9/5 + 32
    }

    fun ToCelsius(selected:String): Double{
        if (this.medidaSeleccionada1.value.equals("Grados Fahrenheit")) {
            return (5/9)*(valor1.value.toDouble()-32.0)
        }
        else return valor1.value.toDouble()-273.0
    }

    fun ToKelvin(selected:String): Double{
        if (this.medidaSeleccionada1.value.equals("Grados Celcius")) {
            return valor1.value.toDouble()+273
        }
        else return ToCelsius("Grados Fahrenheit")+273.0
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    label : String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
){
  var expanded = remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded=expanded.value,
        onExpandedChange = {expanded.value =! expanded.value}
    ) {
        OutlinedTextField(
            value = selectedOption?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)},
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {expanded.value = false}
        ) {
            options.forEach { option->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded.value=false
                    }
                )
            }
        }
    }
}