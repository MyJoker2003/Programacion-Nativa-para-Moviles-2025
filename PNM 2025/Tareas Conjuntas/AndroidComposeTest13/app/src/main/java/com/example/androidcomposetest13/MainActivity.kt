package com.example.androidcomposetest13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidcomposetest13.ui.theme.AndroidComposeTest13Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

//App: Rock,Paper,Scissors
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val sharedViewModel: AppModel = viewModel()
            AndroidComposeTest13Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "firstScreen",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("firstScreen"){
                            FirstScreen(navController,sharedViewModel)
                        }
                        composable("secondScreen"){
                            SecondScreen(navController,sharedViewModel)
                        }
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
    AndroidComposeTest13Theme {
        Greeting("Android")
    }
}

@Composable
fun FirstScreen(navController: NavController, viewModel: AppModel, modifier: Modifier = Modifier){
    val shouldNavigate by viewModel.shouldNavigate
    val isLoading by viewModel.loadingStatus
    var color1 = remember { mutableStateOf(Color.Black) }
    var color2 = remember { mutableStateOf(Color.Black) }
    var color3 = remember { mutableStateOf(Color.Black) }
    var thickness = remember { mutableListOf(2.dp,2.dp,2.dp) }

    LaunchedEffect(shouldNavigate) {
        if (shouldNavigate){
            viewModel.onNavigationHandled()
            navController.navigate("secondScreen")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #13", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (!isLoading){
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "<< ROCK,PAPER,SCISSORS >>", fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "Selecciona una cara:")
                Spacer(modifier = Modifier.padding(8.dp))
                Image(
                    painter = painterResource(R.drawable.piedra),
                    contentDescription = "Piedra",
                    modifier = Modifier
                        .border(width = thickness[0], color = color1.value )
                        .clickable {
                            color1.value = Color.Green
                            color2.value = Color.Black
                            color3.value = Color.Black
                            thickness = mutableListOf(4.dp,2.dp,2.dp)
                            viewModel.updateplayerSelectedIndex(1)
                        }
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Image(
                    painter = painterResource(R.drawable.papel),
                    contentDescription = "Papel",
                    modifier = Modifier
                        .border(width = thickness[1], color = color2.value )
                        .clickable {
                            color1.value = Color.Black
                            color2.value = Color.Green
                            color3.value = Color.Black
                            thickness = mutableListOf(2.dp,4.dp,2.dp)
                            viewModel.updateplayerSelectedIndex(2)
                        }
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Image(
                    painter = painterResource(R.drawable.tijeras),
                    contentDescription = "Tijeras",
                    modifier = Modifier
                        .border(width = thickness[2], color = color3.value )
                        .clickable {
                            color1.value = Color.Black
                            color2.value = Color.Black
                            color3.value = Color.Green
                            thickness = mutableListOf(2.dp,2.dp,4.dp)
                            viewModel.updateplayerSelectedIndex(3)
                        }
                )
                Spacer(modifier = Modifier.padding(14.dp))

                Button(onClick = {viewModel.play()}) {
                    Text(text = "PLAY")
                }

            }else{
                Text(text = "Loading...", fontSize = 20.sp)
                Spacer(modifier = Modifier.padding(8.dp))
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun SecondScreen(navController: NavController,viewModel: AppModel,modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier=Modifier.padding(8.dp))
        Text(text = "APLICACION #13", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "RESULTADO:", fontSize = 27.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(14.dp))
            Text(text = "El jugador eligio:")
            Spacer(modifier = Modifier.padding(8.dp))
            Image(
                painter = painterResource(viewModel.playerSelectedImage.value),
                contentDescription = "playerSelection"
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "El oponenete eligio:")
            Spacer(modifier = Modifier.padding(8.dp))
            Image(
                painter = painterResource(viewModel.machineSelectedImage.value),
                contentDescription = "machineSelection"
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "El resultado fue:")
            Text(text = viewModel.gameResult.value,fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text = "Regresar")
            }
        }
    }
}

class AppModel:ViewModel(){
    val movesList= listOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.piedra,
        R.drawable.papel,
        R.drawable.tijeras
    )
    private var _playerSelectedIndex = mutableStateOf(0)
    val playerSelectedIndex : State<Int> = _playerSelectedIndex

    private var _playerSelectedImage = mutableStateOf(movesList[0])
    val playerSelectedImage : State<Int> = _playerSelectedImage

    private var _machineSelectedIndex = mutableStateOf(0)
    val machineSelectedIndex : State<Int> = _machineSelectedIndex

    private var _machineSelectedImage = mutableStateOf(movesList[0])
    val machineSelectedImage : State<Int> = _machineSelectedImage

    private var _loadingStatus = mutableStateOf(false)
    val loadingStatus:State<Boolean> = _loadingStatus

    private var _gameResult = mutableStateOf("")
    val gameResult : State<String> = _gameResult

    private var _selectedMoveName = mutableStateOf("")
    val selectedMoveName : State<String> = _selectedMoveName

    private var _shouldNavigate = mutableStateOf(false)
    val shouldNavigate:State<Boolean> = _shouldNavigate

    fun onNavigationHandled() {
        _shouldNavigate.value = false // Reinicia la navegación después de ejecutarla
    }

    fun play(){
        if (playerSelectedIndex.value!=0){
            if (!loadingStatus.value){
                _machineSelectedIndex.value = Random.nextInt(1,4)
                _machineSelectedImage.value = movesList[machineSelectedIndex.value]
                detemineWinner()
                _loadingStatus.value=true
                viewModelScope.launch {
                    delay(4000)
                    _loadingStatus.value = false
                    _shouldNavigate.value = true

                }
            }
        }
    }

    fun updateplayerSelectedIndex(value:Int){
        _playerSelectedIndex.value = value
        _playerSelectedImage.value = movesList[value]
    }

    private fun detemineWinner(){
        val player = playerSelectedIndex.value
        val machine = machineSelectedIndex.value

        if (player != machine){
            _gameResult.value = "YOU WIN!!"
            if (player == 1 && machine == 2) _gameResult.value = "YOU LOSE!!!"
            if (player == 2 && machine == 3) _gameResult.value = "YOU LOSE!!!"
            if (player == 3 && machine == 1) _gameResult.value = "YOU LOSE!!!"

        }else _gameResult.value = "EMPATE"
    }
}