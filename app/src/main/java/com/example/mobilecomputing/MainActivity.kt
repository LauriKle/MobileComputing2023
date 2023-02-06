package com.example.mobilecomputing

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilecomputing.ui.login.LoginScreen
import com.example.mobilecomputing.ui.theme.MobileComputingTheme
import com.example.mobilecomputing.CheckPrefCredentials


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val adsasd = CheckPrefCredentials(this)
        adsasd.createAccount(this, "", "")
        adsasd.checkCredentials(this, "" ,"")

        val paskacontext = this
        super.onCreate(savedInstanceState)
        setContent {
            MobileComputingTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column {
                        MobileComputingApp(modifier = Modifier.fillMaxSize(), paskacontext)
                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "JOUUUU $name!")
}

@Composable
fun Greeting2(name: String) {
    Text(text = "HAISTA vittU $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileComputingTheme {
        Greeting("Android")
    }
}