package com.example.mobilecomputing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobilecomputing.ui.home.CreateReminderScreen
import com.example.mobilecomputing.ui.home.HomeScreen
import com.example.mobilecomputing.ui.login.LoginScreen
import com.example.mobilecomputing.ui.profile.ProfileScreen
import com.example.mobilecomputing.ui.createaccount.CreateAccountScreen

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.mobilecomputing.ui.profile.ModifyProfileScreen
import android.content.Context
import android.os.Bundle

data class Paska(
    val id: Int,
    val name: String,
    val time: String,
    val emoji : String
)

val lista = listOf(
    Paska(123,"Aasi","17:43","\uD83D\uDC34"),
    Paska(234,"Sika","12:12","\uD83D\uDC37"),
    Paska(857,"Lehmä","13:00","\uD83D\uDC2E"),
    Paska(123,"Aasi","17:43","\uD83D\uDC34"),
    Paska(234,"Sika","12:12","\uD83D\uDC37"),
    Paska(857,"Lehmä","13:00","\uD83D\uDC2E"),
    Paska(123,"Aasi","17:43","\uD83D\uDC34"),
    Paska(234,"Sika","12:12","\uD83D\uDC37"),
    Paska(857,"Lehmä","13:00","\uD83D\uDC2E"),
    Paska(123,"Aasi","17:43","\uD83D\uDC34"),
    Paska(234,"Sika","12:12","\uD83D\uDC37"),
    Paska(857,"Lehmä","13:00","\uD83D\uDC2E"),
)

data class Profile(
    var username: String,
    var password: String,
    val id: Int,
)

val feikki_profiili = Profile("asd", "asd", 12345)

@Composable
fun MobileComputingApp(
    modifier: Modifier,
    context: Context,
    appState: MobileComputingAppState = rememberMobileComputingAppState(),
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        val sharedPreferences =
        composable(route = "login") {
            LoginScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController, context = context)
        }
        composable(route = "home") {
            HomeScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController, paskaList = lista)
        }
        composable(route = "createReminder") {
            CreateReminderScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController) // , onBackPress = appState::navigateBack
        }
        composable(route = "profile") {
            ProfileScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController , onBackPress = appState::navigateBack, context = context) // , onBackPress = appState::navigateBack
        }
        composable(route = "modifyProfile") {
            ModifyProfileScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController , onBackPress = appState::navigateBack, context = context) // , onBackPress = appState::navigateBack
        }
        composable(route = "createAccount") {
            CreateAccountScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController , onBackPress = appState::navigateBack, context = context) // , onBackPress = appState::navigateBack
        }
    }
}