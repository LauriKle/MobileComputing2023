package com.example.mobilecomputing.ui.home

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilecomputing.ui.home.reminder.ReminderViewModel
import com.example.mobilecomputing.ui.home.reminder.ReminderViewState
import com.mobilecomputing.core_domain.entity.Reminder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//var showAllReminders = false

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavController,
    //paskaList: List<Paska>,
    viewModel: ReminderViewModel = hiltViewModel(),
) {
    var showAllReminders by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        topBar = {
                TopAppBar(
                    title = { Text(text = "Reminders") },
                    actions = {
                        IconButton(
                            onClick = { navController.navigate("profile") }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Open profile screen"
                            )
                        }
                        IconButton(
                            onClick = { navController.navigate("virtualLocationMap") }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Open virtual location map"
                            )
                        }
                        IconButton(
                            onClick = {
                                showAllReminders = !showAllReminders
                                println("######" + showAllReminders)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Switch showing all reminders on or off"
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open Navigation Drawer"
                            )
                        }
                    }
                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "createReminder") },
                //contentColor = Color.Blue,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {        Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ReminderList(
            reminderViewModel = viewModel,
            NavController = navController,
            showAllReminders = showAllReminders
        )
    }
    }
}

@Composable
private fun ReminderList(
    reminderViewModel: ReminderViewModel,
    NavController: NavController,
    showAllReminders: Boolean
) {
    reminderViewModel.loadReminders()
    val reminderViewState by reminderViewModel.uiState.collectAsState()
    println(reminderViewState)
    when (reminderViewState) {
        is ReminderViewState.Loading -> {}
        is ReminderViewState.Success -> {
            val reminderList = (reminderViewState as ReminderViewState.Success).data

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.Center
            ) {
                items(reminderList.filter { reminder ->
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    try {
                        val reminderTime = dateFormat.parse(reminder.reminder_time) ?: return@filter false
                        val reminderCalendar = Calendar.getInstance().apply {
                            time = reminderTime
                        }
                        if (showAllReminders) {
                            true
                        } else {
                            reminderCalendar.timeInMillis < Calendar.getInstance().timeInMillis
                        }
                    } catch (e: ParseException) {
                        //Time value is invalid.
                        true
                    }
                }) { item ->
                    ReminderListItem(
                        reminder = item,
                        onClick = {},
                        navController = NavController
                    )

                }
            }
        }
        else -> {}
    }

}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(

        onClick = {
            navController.navigate("editReminder/${reminder.reminderId}/${reminder.message}/${reminder.reminder_time}/${reminder.emoji}")
                  },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = reminder.emoji,
                    fontSize = 32.sp,
                )
            Column()  {
            Text(
                reminder.message + " at " + reminder.reminder_time,
            )
            Text(
                "By " + reminder.creator_id.toString() + " at " + reminder.creation_time,
            )
        }
        }

    }
    Spacer(modifier = Modifier.height(16.dp))
}
