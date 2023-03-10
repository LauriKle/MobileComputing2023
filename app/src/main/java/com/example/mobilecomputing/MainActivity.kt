package com.example.mobilecomputing

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mobilecomputing.ui.login.LoginScreen
import com.example.mobilecomputing.ui.theme.MobileComputingTheme
import com.example.mobilecomputing.CheckPrefCredentials
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import android.app.Activity
import android.os.Looper
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val adsasd = CheckPrefCredentials(this)
        adsasd.createAccount(this, "", "")
        adsasd.checkCredentials(this, "" ,"")

        val paskacontext = this
        super.onCreate(savedInstanceState)

        val paskactivity = this

        val locationRequest = LocationRequest.create().apply {
            interval = 5000 // 5 seconds
            fastestInterval = 2000 // 2 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }



        setContent {
            MobileComputingTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column {
                        MobileComputingApp(modifier = Modifier.fillMaxSize(), paskacontext, paskactivity)
                    }
                }
            }
        }
    }

}
