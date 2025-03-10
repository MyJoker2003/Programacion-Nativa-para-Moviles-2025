package com.example.composearticle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composearticle.ui.theme.ComposeArticleTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeArticleTheme {
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
    ComposeArticleTheme {
        Article()
    }
}

@Composable
fun Article(modifier: Modifier = Modifier){
    Column (
        modifier = modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.bg_compose_background),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Row (modifier = Modifier.align(Alignment.Start)) {
            Text(
                text = stringResource(R.string.article_title_text),
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(
            text = stringResource(R.string.first_paragraph_text),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
        )
        Text(
            text = stringResource(R.string.proposito_text),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                bottom = 16.dp
            )
        )
    }
}