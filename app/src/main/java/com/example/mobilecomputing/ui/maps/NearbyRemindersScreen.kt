package com.example.mobilecomputing.ui.maps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilecomputing.R
import com.example.mobilecomputing.ui.home.reminder.ReminderViewModel
import com.example.mobilecomputing.ui.home.reminder.ReminderViewState
import com.mobilecomputing.core_domain.entity.Reminder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*

@Composable
fun NearbyRemindersScreen(
    navController: NavController,
    latitude: Double,
    longitude: Double,
    viewModel: ReminderViewModel = hiltViewModel(),
) {
    println("Coords:" + latitude + " " + longitude)
    viewModel.loadReminders()
    val reminderViewState by viewModel.uiState.collectAsState()
   // val latitude = latitude.toDouble()
   // val longitude = longitude.toDouble()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Nearby Reminders") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
                actions = {
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (reminderViewState) {
                is ReminderViewState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is ReminderViewState.Success -> {
                    val reminderList = (reminderViewState as ReminderViewState.Success).data
                    println(reminderList)
                    val nearbyReminders = reminderList.filter { reminder ->
                        if (reminder.location_x.isBlank() || reminder.location_y.isBlank()) {
                            return@filter false
                        }

                        val reminderLong = reminder.location_x.toDoubleOrNull() ?: return@filter false
                        val reminderLat = reminder.location_y.toDoubleOrNull() ?: return@filter false

                        val latDiff = abs(reminderLat - latitude)
                        val longDiff = abs(reminderLong - longitude)

                        // 1 degree of latitude and longitude is about 111 km
                        // so we'll use 0.009 degrees as the equivalent of 1 km
                        latDiff <= 0.009 && longDiff <= 0.009
                    }

                    if (nearbyReminders.isEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = "No nearby reminders found!",
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(nearbyReminders) { reminder ->
                                ReminderListItem(
                                    reminder = reminder,
                                    onClick = {},
                                    navController = navController,
                                    viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    navController: NavController,
    viewModel: ReminderViewModel,
) {
    OutlinedButton(
        onClick = {
         //   navController.navigate(
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = reminder.emoji,
                fontSize = 32.sp,
            )
            Column() {
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