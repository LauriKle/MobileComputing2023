package com.example.mobilecomputing.ui.createaccount

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputing.feikki_profiili
import android.content.Context
import android.os.Bundle
import com.example.mobilecomputing.CheckPrefCredentials

@Composable
fun CreateAccountScreen(
    modifier: Modifier,
    context: Context,
    navController: NavController,
    onBackPress: () -> Unit,
) {
    val newUsername = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "Create Account") },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPress() }
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

            Text("Input a username and a password to create your account!")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Username") },
                value = newUsername.value,
                onValueChange = { text -> newUsername.value = text },
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                value = newPassword.value,
                onValueChange = { text -> newPassword.value = text },
                shape = RoundedCornerShape(24.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val adsasd = CheckPrefCredentials(context)
                    val usrnm = adsasd.createAccount(context, newUsername.value, newPassword.value)
                    navController.navigate("login")
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Create Account!")
            }
        }
    }
}