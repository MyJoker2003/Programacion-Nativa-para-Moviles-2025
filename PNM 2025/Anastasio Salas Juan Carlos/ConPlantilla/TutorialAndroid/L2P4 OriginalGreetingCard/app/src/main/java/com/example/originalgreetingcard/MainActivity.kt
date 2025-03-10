package com.example.originalgreetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.originalgreetingcard.ui.theme.OriginalGreetingCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OriginalGreetingCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
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
    OriginalGreetingCardTheme {
        BussinessCard()
    }
}

@Composable
fun BussinessCard(modifier: Modifier=Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize().background(Color.Cyan)
    ) {
        PresentationCard()
        ContactMethood()

    }
}

@Composable
fun ContactListElement(icon: ImageVector, item:String){
    Row(
        modifier = Modifier.padding(
            start = 20.dp, end = 20.dp
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(
                end = 10.dp
            )
        )
        Text(
            text = item,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun ContactMethood(){
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            ContactListElement(Icons.Filled.Call, stringResource(R.string.phone_number))
            ContactListElement(Icons.Filled.Share, stringResource(R.string.media_id_text))
            ContactListElement(Icons.Filled.Email, stringResource(R.string.email_text))
        }
    }
}

@Composable
fun PresentationCard(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.width(150.dp).height(150.dp).background(Color.Gray)
        ) {
            Image(
                painter = painterResource(R.drawable.android_logo),
                contentDescription = null
            )
        }
        Text(
            text = stringResource(R.string.name_text),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = stringResource(R.string.title_text)
        )
    }
}