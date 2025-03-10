package com.example.androidcomposetest3

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcomposetest3.ui.theme.AndroidComposeTest3Theme

//Aplicacion Contador
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposeTest3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun MainApp(modifier: Modifier = Modifier){
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("MyAppVal",Context.MODE_PRIVATE)
    }
    val MY_VARIABLE_KEY = "0"

    var CounterValue = remember { mutableIntStateOf(sharedPreferences.getInt(MY_VARIABLE_KEY,0)) }

    LaunchedEffect(CounterValue) {
        sharedPreferences.edit().putInt(MY_VARIABLE_KEY,CounterValue.value).apply()
    }

    Column (
        modifier = Modifier.fillMaxSize().background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #3")
        Spacer(modifier=Modifier.padding(10.dp))
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Green),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "<< CONTADOR >>", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier=Modifier.padding(12.dp))
            Text(text = CounterValue.value.toString(), fontSize = 70.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier=Modifier.padding(16.dp))
            Button(onClick = {CounterValue.value++}) {
                Text(text = "Incrementar", fontSize = 15.sp)
            }
            Spacer(modifier=Modifier.padding(8.dp))
            Button(onClick = {CounterValue.value=0}) {
                Text(text = "Reset", fontSize = 15.sp)
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
    AndroidComposeTest3Theme {
        MainApp()
    }
}