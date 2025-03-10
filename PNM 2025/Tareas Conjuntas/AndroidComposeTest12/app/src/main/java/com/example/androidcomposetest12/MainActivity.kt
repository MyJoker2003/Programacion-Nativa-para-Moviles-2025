package com.example.androidcomposetest12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.androidcomposetest12.ui.theme.AndroidComposeTest12Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeTest12Theme {
                val navController = rememberNavController()
                val sharedViewModel: AppModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "firstScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("firstScreen") {
                            FirstScreen(navController, sharedViewModel)
                        }
                        composable("secondScreen") {
                            SecondScreen(navController, sharedViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.coinaguila),
            contentDescription = "Mondeda"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidComposeTest12Theme {
        Greeting("ss")
    }
}

@Composable
fun FirstScreen(navController: NavController,viewModel: AppModel,modifier: Modifier = Modifier){
    val shouldNavigate by viewModel.shouldNavigate
    val isLoading by viewModel.loadingStatus
    var color1 = remember { mutableStateOf(Color.Black) }
    var color2 = remember { mutableStateOf(Color.Black) }
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
        Text(text = "APLICACION #10", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (!isLoading) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "<< TOSS COIN >>", fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "Selecciona una cara:")
                Spacer(modifier = Modifier.padding(8.dp))
                Row {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                BorderStroke(2.dp, color1.value),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ){
                        Image(
                            painter = painterResource(R.drawable.coinsol),
                            contentDescription = "Aguila",
                            modifier = Modifier.clickable {
                                color1.value = Color.Green
                                color2.value = Color.Black
                                viewModel.updateplayerSelectedIndex(1)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                BorderStroke(2.dp, color2.value),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ){
                        Image(
                            painter = painterResource(R.drawable.coinaguila),
                            contentDescription = "Aguila",
                            modifier = Modifier.clickable {
                                color2.value = Color.Green
                                color1.value = Color.Black
                                viewModel.updateplayerSelectedIndex(2)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = {viewModel.Toss()}) {
                    Text(text = "TOSS")
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
    var resultmessage = remember { mutableStateOf("") }
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
            Text(text = "RESULTADO:", fontSize = 27.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(14.dp))
            Text(text = "El jugador eligio:")
            Spacer(modifier = Modifier.padding(8.dp))
            Image(
                painter = painterResource(viewModel.playerSelectedImage.value),
                contentDescription = "playerSelection"
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "El resultado fue:")
            Spacer(modifier = Modifier.padding(8.dp))
            Image(
                painter = painterResource(viewModel.machineSelectedImage.value),
                contentDescription = "machineSelection"
            )
            if (viewModel.playerSelectedImage.value == viewModel.machineSelectedImage.value){
                resultmessage.value = "YOU WIN!!"

            }else{
                resultmessage.value = "YOU LOSE!!!"
            }
            Text(resultmessage.value, fontSize = 40.sp, fontWeight = FontWeight.Bold)

            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text = "Regresar")
            }
        }
    }
}

class AppModel:ViewModel(){
    val coinList = listOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.coinsol,
        R.drawable.coinaguila
    )

    private var _playerSelectedIndex = mutableStateOf(0)
    val playerSelectedIndex : State<Int> = _playerSelectedIndex

    private var _playerSelectedImage = mutableStateOf(coinList[0])
    val playerSelectedImage : State<Int> = _playerSelectedImage

    private var _machineSelectedIndex = mutableStateOf("")
    val machineSelectedIndex : State<String> = _machineSelectedIndex

    private var _machineSelectedImage = mutableStateOf(coinList[0])
    val machineSelectedImage : State<Int> = _machineSelectedImage

    private var _loadingStatus = mutableStateOf(false)
    val loadingStatus:State<Boolean> = _loadingStatus

    private var _coinTossResult = mutableStateOf("")
    val coinTossResult : State<String> = _coinTossResult

    private var _shouldNavigate = mutableStateOf(false)
    val shouldNavigate:State<Boolean> = _shouldNavigate

    fun Toss(){

        if (playerSelectedIndex.value != 0) {
            if (!loadingStatus.value){
                _machineSelectedImage.value = coinList.filter { it != R.drawable.ic_launcher_foreground }.random()
                _loadingStatus.value=true
                viewModelScope.launch {
                    delay(4000)
                    _loadingStatus.value = false
                    _shouldNavigate.value = true
                }
            }
        }
    }
    fun onNavigationHandled() {
        _shouldNavigate.value = false // Reinicia la navegación después de ejecutarla
    }

    fun updateloadingStatus(value:Boolean){
        _loadingStatus.value = value
    }

    fun updateplayerSelectedIndex(value:Int){
        _playerSelectedIndex.value = value
        _playerSelectedImage.value = coinList[value]
    }
}