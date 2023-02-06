package com.example.mobilecomputing.ui.home

import android.widget.TextView
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CreateReminderScreen(
    modifier: Modifier,
    navController: NavController
)  {
    val reminderName = remember { mutableStateOf("") }
    val reminderTime = remember { mutableStateOf("") }
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

        //Icon(
        //    painter = rememberVectorPainter(Icons.Filled.Person),
        //    contentDescription = "login_icon",
        //    modifier = Modifier.fillMaxWidth().size(256.dp),
        //)
        Text("Teeppä uus reminderi!")

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nimi") },
            value =reminderName.value,
            onValueChange = { text -> reminderName.value = text },
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("aika") },
            value = reminderTime.value,
            onValueChange = { text -> reminderTime.value = text },
            shape = RoundedCornerShape(24.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Tee uus reminder")
        }
    }

}
