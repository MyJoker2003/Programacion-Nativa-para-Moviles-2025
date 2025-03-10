package com.example.androidcomposetest2

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameModel : ViewModel(){
    var TryCounter = mutableIntStateOf(0)
    var number =  mutableStateOf("")
    var result = mutableStateOf("")
    var randomNumber = mutableIntStateOf(generateRandomNum())
    var playFinished =  mutableStateOf(false)

    private fun generateRandomNum():Int{
        return Random.nextInt(1,4)
    }

    fun regenerateRandomNum(){
        this.randomNumber.value = generateRandomNum();
        this.number.value=""
        this.TryCounter.value=0
        this.playFinished.value = false
        this.result.value = ""
    }

    fun Adivina(){

        val promptVal: Int? = number.value.toIntOrNull()
        if (promptVal != null){
            TryCounter.value++
            when{
                (promptVal == randomNumber.value) ->{
                    this.result.value =
                                "CORRECTO\n"+
                                "Num Intentos ${TryCounter.value}"
                    if (TryCounter.value == 1) this.result.value+="\n\nLUCKY GUY (>_<)"
                    this.playFinished.value=true
                }
                (promptVal < randomNumber.value)->{
                    this.result.value =
                        "INCORRECTO (T_T)\n"+
                        "Num Intentos ${TryCounter.value}\n"+
                        "Pista: El numero es Mayor"
                }
                (promptVal > randomNumber.value)->{
                    this.result.value =
                        "INCORRECTO (T_T)\n" +
                        "Num Intentos ${TryCounter.value}\n"+
                        "Pista: El numero es Menor"
                }
            }
        }
    }
}