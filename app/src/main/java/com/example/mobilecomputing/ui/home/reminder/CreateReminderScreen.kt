package com.example.mobilecomputing.ui.home.reminder

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.tooling.data.EmptyGroup.location
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.mobilecomputing.core_domain.entity.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilecomputing.CheckPrefCredentials
import com.example.mobilecomputing.GeofenceBroadcastReceiver
import com.example.mobilecomputing.GeofencingHelper
import com.example.mobilecomputing.GeofencingHelper.Companion.ACTION_GEOFENCE_EVENT
import java.text.SimpleDateFormat
import java.util.*
import com.example.mobilecomputing.ui.createWork
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import java.nio.channels.NonReadableChannelException
import java.text.ParseException

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CreateReminderScreen(
    modifier: Modifier,
    navController: NavController,
    context: Context,
    viewModel: ReminderViewModel = hiltViewModel(),
    activity: Activity
) {

    val geofencingHelper = remember { GeofencingHelper(context) }
    val geofenceIntent = remember {
        Intent(context, GeofenceBroadcastReceiver::class.java).apply {
            action = GeofencingHelper.ACTION_GEOFENCE_EVENT
        }
    }

    //val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)
    //activity.registerReceiver(GeofenceBroadcastReceiver(), IntentFilter(ACTION_GEOFENCE_EVENT))

    val adsasd = CheckPrefCredentials(context)
    val usrnm = adsasd.getUsername(context)
    val reminderName = remember { mutableStateOf("") }
    val reminderTime = remember { mutableStateOf("") }
    val emojis = listOf("😊", "🍕", "⏰", "🎉", "💩")
    var selectedEmoji by remember { mutableStateOf("") }
    val notifyUser = remember { mutableStateOf(false) }

    val recordAudioRequestCode = 1001
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val speechRecognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
          //  putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000)
          //  putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1000)
        }
    }
    var isListening by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )
    val context = LocalContext.current

    val locationData = rememberSaveable { mutableStateOf<Bundle?>(null) }

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    savedStateHandle?.getLiveData<Bundle>("location_data")?.observe(
        LocalLifecycleOwner.current,
        { bundle ->
            locationData.value = bundle
        }
    )

    Column(
        modifier = modifier.padding(25.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Create a new reminder!")

        Spacer(modifier = Modifier.height(24.dp))
        if (ContextCompat.checkSelfPermission(LocalContext.current, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), recordAudioRequestCode)
        }
        Button(
            onClick = {
                isListening = true
                speechRecognizer.startListening(speechRecognizerIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Speak your message")
        }

        if (isListening) {
            DisposableEffect(Unit) {
                val listener = object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {}
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {}
                    override fun onError(error: Int) {
                        println("tuli joku error" + error)
                    }
                    override fun onResults(results: Bundle?) {
                        val text = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
                        messageText += " $text"
                        isListening = false
                    }
                    override fun onPartialResults(partialResults: Bundle?) {}
                    override fun onEvent(eventType: Int, params: Bundle?) {}
                }

                speechRecognizer.setRecognitionListener(listener)

                onDispose {
                    speechRecognizer.setRecognitionListener(null)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        //reminderName.value = recognizedText
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Message") },
            value = messageText,
            onValueChange = { messageText = it },
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Time") },
            value = reminderTime.value,
            onValueChange = { text -> reminderTime.value = text },
            shape = RoundedCornerShape(24.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))

        LazyRow {
            items(emojis) { emoji ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .then(
                            if (selectedEmoji == emoji) Modifier.background(
                                Color.Gray,
                                CircleShape
                            ) else Modifier
                        )
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = notifyUser.value,
                onCheckedChange = { notifyUser.value = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Notify me at the correct time")
        }

        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {
                requestPermission(
                    context = context,
                    permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    requestPermission = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
                )                .apply {requestPermission(
                        context = context,
                permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                requestPermission = { launcher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION) }
                )}
                    .apply {
                    navController.navigate("map")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Choose location")
        }
        Spacer(modifier = Modifier.height(8.dp))
        val latitude = locationData.value?.getDouble("latitude")
        val longitude = locationData.value?.getDouble("longitude")
        val coordinates = if (latitude != null && longitude != null) {
            "Latitude: ${String.format("%.3f", latitude)}, Longitude: ${String.format("%.3f", longitude)}"
        } else {
            "Coordinates not set yet"
        }
        Text(text = coordinates)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {

                val latitudee = locationData.value?.getDouble("latitude")
                val longitudee = locationData.value?.getDouble("longitude")
                var xcoord = longitudee.toString()
                var ycoord = latitudee.toString()
                if (latitudee == null && longitudee == null) {
                    xcoord = "Null"
                    ycoord = "Null"
                }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentDateTimeString = dateFormat.format(Date())
                var remTime = reminderTime.value
                try {
                    val date = dateFormat.parse(reminderTime.value)
                }catch (e: ParseException) {
                    remTime = "No time"
                }
                val reminder = Reminder(
                    message = messageText,
                    location_x = xcoord,
                    location_y = ycoord,
                    reminder_time = remTime,
                    creation_time = currentDateTimeString,
                    creator_id = usrnm,
                    reminder_seen = false,
                    emoji = selectedEmoji,
                )
                viewModel.saveReminder(reminder)
                try {
                    val date = dateFormat.parse(reminderTime.value)
                    if (notifyUser.value == true) {
                     createWork(context, reminder)
                    }
                } catch (e: ParseException) {
                        // If the parse fails, time was not in the correct format
                    println("ReminderTime was not in the correct format.")
                }
                if (latitudee != null && longitudee != null) {
                    //Create geofence
                    val geofence = Geofence.Builder()
                        .setRequestId(reminder.message.toString())
                        .setCircularRegion(latitudee, longitudee, 50F)
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                        .build()
                    val geofenceList = listOf(geofence)
                    geofencingHelper.addGeofences(geofenceList, geofenceIntent)
                }
                navController.navigate("home")

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Create Reminder")
        }
    }
}

private fun requestPermission(
    context: Context,
    permission: String,
    requestPermission: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermission()
    }
}
