package com.example.androidcomposetest11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcomposetest11.ui.theme.AndroidComposeTest11Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val myviewModel : DiceModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest11Theme {
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
    AndroidComposeTest11Theme {
        AppScreen(DiceModel())
    }
}

@Composable
fun AppScreen(viewModel: DiceModel,modifier: Modifier=Modifier){
    val isLoading by viewModel.loadingStatus
    val currentImg by viewModel.currentImage
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
            Text(text = "<< ROLL DICE >>", fontSize = 27.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(16.dp))

            if (!isLoading){
                Image(
                    painter = painterResource(id = currentImg),
                    contentDescription = "Dice"
                )
            }else{
                CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(onClick ={viewModel.rollDice()} ) {
                Text(text = "ROLL", fontSize = 18.sp)
            }
        }
    }
}

class DiceModel:ViewModel(){
    val DiceImageList = listOf(
        R.drawable.dice0,
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    )
    private var _currentImage = mutableStateOf(DiceImageList[0])
    val currentImage:State<Int> = _currentImage
    private var _loadingStatus = mutableStateOf(false)
    val loadingStatus:State<Boolean> = _loadingStatus

    fun rollDice(){
        if (!loadingStatus.value){
            _loadingStatus.value = true
            viewModelScope.launch {
                delay(5000)
                _currentImage.value=DiceImageList.filter { it != R.drawable.dice0 }.random()
                _loadingStatus.value = false
            }
        }
    }
}