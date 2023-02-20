package com.example.mobilecomputing.ui.login

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputing.CheckPrefCredentials
import com.example.mobilecomputing.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController,
    context: Context,
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = modifier.padding(25.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
//        Image(
 //           painter = painterResource(id = R.drawable.ic_launcher_foreground),
  //          contentDescription = "login_image",
  //          modifier = Modifier.fillMaxWidth(),
   //         alignment = Alignment.Center
    //    )

        Icon(
            painter = rememberVectorPainter(Icons.Filled.Person),
            contentDescription = "login_icon",
            modifier = Modifier.fillMaxWidth().size(256.dp),
        )
        Text("Please log in!")

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Username")},
            value = username.value,
            onValueChange = { text -> username.value = text },
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password")},
            value = password.value,
            onValueChange = { text -> password.value = text },
            shape = RoundedCornerShape(24.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (checkLogin(username.value, password.value, context)){
                    navController.navigate("home")
                }
                      },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                    navController.navigate("createAccount")
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Create Account")
        }
    }
}

fun checkLogin(usrnm:String, passwd:String, context:Context) : Boolean {
    val adsasd = CheckPrefCredentials(context)
    val succ = adsasd.checkCredentials(context, usrnm, passwd)
    //val succ = (passwd == feikki_profiili.password && usrnm == feikki_profiili.username)
    return succ
}
