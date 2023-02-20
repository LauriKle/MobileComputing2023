package com.example.mobilecomputing

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobilecomputing.ui.home.HomeScreen
import com.example.mobilecomputing.ui.login.LoginScreen
import com.example.mobilecomputing.ui.profile.ProfileScreen
import com.example.mobilecomputing.ui.createaccount.CreateAccountScreen

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.mobilecomputing.ui.profile.ModifyProfileScreen
import android.content.Context
import android.os.Bundle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.mobilecomputing.ui.home.reminder.CreateReminderScreen
import com.example.mobilecomputing.ui.home.reminder.EditReminderScreen
import com.example.mobilecomputing.ui.home.reminder.ReminderViewModel

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
)

data class Profile(
    var username: String,
    var password: String,
    val id: Int,
)

@Composable
fun MobileComputingApp(
    modifier: Modifier,
    context: Context,
    activity: Activity,
    appState: MobileComputingAppState = rememberMobileComputingAppState(),
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        //val sharedPreferences =
        composable(route = "login") {
            LoginScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController, context = context)
        }
        composable(route = "home") {
            HomeScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController)
        }
        composable(route = "createReminder") {
            CreateReminderScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController, context = context, activity = activity) // , onBackPress = appState::navigateBack
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

        composable(
            route = "editReminder/{reminderId}/{message}/{reminder_time}/{emoji}",
            arguments = listOf(
                navArgument("reminderId") { type = NavType.StringType },
                navArgument("message") { type = NavType.StringType },
                navArgument("reminder_time") { type = NavType.StringType } ,
                navArgument("emoji") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val reminderId = backStackEntry.arguments?.getString("reminderId") ?: ""
            val message = backStackEntry.arguments?.getString("message") ?: ""
            val reminderTime = backStackEntry.arguments?.getString("reminder_time") ?: ""
            var emoji = backStackEntry.arguments?.getString("emoji") ?: ""
            EditReminderScreen(
                modifier = Modifier.fillMaxSize(),
                navController = appState.navController,
                context = context,
                reminderId,
                message,
                reminderTime,
                emoji
            )
        }
    }
}