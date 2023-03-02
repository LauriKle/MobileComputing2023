package com.example.mobilecomputing.ui.home.reminder

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
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
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import java.text.SimpleDateFormat
import java.util.*
import com.example.mobilecomputing.ui.createWork

@Composable
fun CreateReminderScreen(
    modifier: Modifier,
    navController: NavController,
    context: Context,
    viewModel: ReminderViewModel = hiltViewModel(),
    activity: Activity
) {
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
            modifier = Modifier.fillMaxWidth().height(64.dp),
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = notifyUser.value,
                onCheckedChange = { notifyUser.value = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Make a notification")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentDateTimeString = dateFormat.format(Date())
                val reminder =                     Reminder(
                    message = messageText,
                    location_x = "111",
                    location_y = "222",
                    reminder_time = reminderTime.value,
                    creation_time = currentDateTimeString,
                    creator_id = usrnm,
                    reminder_seen = false,
                    emoji = selectedEmoji,
                )
                viewModel.saveReminder(reminder)
                if (notifyUser.value == true) {
                    createWork(context, reminder)
                }
                navController.navigate("home")

            },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Create Reminder")
        }
    }
}