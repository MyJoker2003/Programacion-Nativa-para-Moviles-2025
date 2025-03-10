package com.example.cuadrantecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cuadrantecompose.ui.theme.CuadranteComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        EdgeToEdge hace que la app tome toda la pantalla, incluyento la barra de notificaciones
        * */
        enableEdgeToEdge()
        setContent {
            CuadranteComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        QuadrantScreen()
                    }
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
    CuadranteComposeTheme {
        QuadrantScreen()
    }
}

@Composable
fun QuadrantScreen(modifier: Modifier = Modifier){
    Column (
        modifier = modifier.fillMaxWidth()
    ){
        Row (Modifier.weight(1f)) {
            Cuadrante(
                title = stringResource(R.string.text_description_title),
                message = stringResource(R.string.text_description_text),
                color = Color(0xFFEADDFF),
                modifier = modifier.weight(1f)
            )
            Cuadrante(
                title = stringResource(R.string.image_description_title),
                message = stringResource(R.string.image_description_text),
                color = Color(0xFFD0BCFF),
                modifier = modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)) {
            Cuadrante(
                title = stringResource(R.string.row_description_title),
                message = stringResource(R.string.row_description_text),
                color = Color(0xFFB69DF8),
                modifier = modifier.weight(1f)
            )
            Cuadrante(
                title = stringResource(R.string.column_description_title),
                message = stringResource(R.string.column_description_text),
                color = Color(0xFFF6EDFF),
                modifier = modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Cuadrante(title:String,message:String,color: Color, modifier: Modifier = Modifier){
    Column(
        //verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color)
            .fillMaxSize()
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(16.dp)
        ){
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = message,
                textAlign = TextAlign.Justify
            )
        }
    }
}