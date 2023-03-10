package com.example.mobilecomputing

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mobilecomputing.GeofencingHelper.Companion.ACTION_GEOFENCE_EVENT
import com.example.mobilecomputing.ui.getEmojiBitmap
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "GEOFENCE EVENT Received!!!!")
        if (intent != null && context != null) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            val geofenceTransition = geofencingEvent.geofenceTransition

            if (geofencingEvent.hasError()) {
                Log.e(TAG, "Geofencing error: ${geofencingEvent.errorCode}")
                return
            }

            val geofences = geofencingEvent.triggeringGeofences
            println("Geofencetransition was: " + geofenceTransition)
            if (geofences.isNullOrEmpty()) {
                Log.e(TAG, "No geofences received for some reason.")
                return
            }

            val geofence = geofences[0]

            when (geofenceTransition) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> {
                    Log.i(TAG, "Entered geofence: ${geofence.requestId}")
                    // Notification
                    val importance = NotificationManager.IMPORTANCE_HIGH
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val channel = NotificationChannel("channel_id", "channelName", importance)
                    notificationManager.createNotificationChannel(channel)
                    val notificationId = 1
                    val builder = NotificationCompat.Builder(context, "channel_id")
                        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                        .setContentTitle("Location Reminder!")
                        .setContentText(geofence.requestId)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    notificationManager.notify(notificationId, builder.build())
                }
                Geofence.GEOFENCE_TRANSITION_EXIT -> {
                    Log.i(TAG, "Exited geofence: ${geofence.requestId}")
                    println("Exited geofence: ${geofence.requestId}")
                }
                Geofence.GEOFENCE_TRANSITION_DWELL -> {
                    Log.i(TAG, "Dwelling in geofence: ${geofence.requestId}")
                    println("Dwelling in geofence: ${geofence.requestId}")
                }
                else -> {
                    Log.e(TAG, "Invalid transition type: $geofenceTransition")
                }
            }
        }
    }

    companion object {
        private const val TAG = "GeofenceBroadcastReceiver"
    }
}