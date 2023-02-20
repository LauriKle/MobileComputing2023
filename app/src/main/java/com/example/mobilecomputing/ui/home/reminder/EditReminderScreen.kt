package com.example.mobilecomputing.ui.home.reminder

import android.content.Context
import android.os.Build
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilecomputing.core_domain.entity.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilecomputing.CheckPrefCredentials
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditReminderScreen(
    modifier: Modifier,
    navController: NavController,
    context: Context,
    reminderId: String,
    message: String,
    reminderTime: String,
    emoji: String,
    viewModel: ReminderViewModel = hiltViewModel(),
)  {

    val adsasd = CheckPrefCredentials(context)
    val usrnm = adsasd.getUsername(context)
    var ReminderName by remember { mutableStateOf(message) }
    var ReminderTime by remember { mutableStateOf(reminderTime) }
    val emojis = listOf("😊", "🍕", "⏰", "🎉", "💩")
    var selectedEmoji by remember { mutableStateOf("") }

    selectedEmoji = emoji

    Column(
        modifier = modifier.padding(25.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Modify the existing reminder!")

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Message") },
            value = ReminderName,
            onValueChange = { text -> ReminderName = text },
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Time") },
            value = ReminderTime,
            onValueChange = { text -> ReminderTime = text },
            shape = RoundedCornerShape(24.dp)
        )
        LazyRow {
            items(emojis) { emoji ->
                Box(
                    modifier = Modifier.padding(8.dp)
                        .then(if (selectedEmoji == emoji) Modifier.background(Color.Gray, CircleShape) else Modifier)
                ) {
                    IconButton(
                        onClick = { selectedEmoji = emoji }) {
                        Text(
                            text = emoji,
                            fontSize = 32.sp,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentDateTimeString = dateFormat.format(Date())
                viewModel.saveReminder(
                    Reminder(
                        reminderId = reminderId.toLong(),
                        message = ReminderName,
                        location_x = "111",
                        location_y = "222",
                        reminder_time = ReminderTime,
                        creation_time = currentDateTimeString,
                        creator_id = usrnm,
                        reminder_seen = false,
                        emoji = selectedEmoji
                    )
                )
                navController.navigate("home")
            },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Update Reminder")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            onClick = {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentDateTimeString = dateFormat.format(Date())
                viewModel.deleteReminder(
                    Reminder(
                        reminderId = reminderId.toLong(),
                        message = ReminderName,
                        location_x = "111",
                        location_y = "222",
                        reminder_time = ReminderTime,
                        creation_time = currentDateTimeString,
                        creator_id = usrnm,
                        reminder_seen = false,
                        emoji = "💩"
                    )
                )
                navController.navigate("home")
            },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Delete Reminder")
        }

    }

}
