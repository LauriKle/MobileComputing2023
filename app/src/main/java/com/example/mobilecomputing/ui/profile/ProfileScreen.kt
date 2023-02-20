package com.example.mobilecomputing.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.content.Context
import android.os.Bundle
import com.example.mobilecomputing.CheckPrefCredentials

@Composable
fun ProfileScreen(
    modifier: Modifier,
    context: Context,
    navController: NavController,
    onBackPress: () -> Unit,
)   {
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(
                        onClick = {onBackPress()}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go back to previous screen"
                        )
                    }
                }
            )
        }
    )
    {
        Column(
            modifier = modifier.padding(25.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.Filled.Person),
                contentDescription = "login_icon",
                modifier = Modifier.fillMaxWidth().size(256.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))

            val adsasd = CheckPrefCredentials(context)
            val usrnm = adsasd.getUsername(context)

            Text("Username: " + usrnm)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("modifyProfile") },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Change Password or Username")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Log Out")
            }
        }
    }
}