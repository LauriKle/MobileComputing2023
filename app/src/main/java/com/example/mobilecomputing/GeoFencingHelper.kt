package com.example.mobilecomputing
import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeofencingHelper(private val context: Context) {
    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

    fun addGeofences(geofences: List<Geofence>, intent: Intent) {
        val geofencingRequest = getGeofencingRequest(geofences)
        val pendingIntent = getGeofencePendingIntent(intent)

        for (geofence in geofences) {
            Log.d(TAG, "Added geofence: ${geofence.requestId}")
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, add geofences
            geofencingClient.addGeofences(geofencingRequest, pendingIntent)?.run {
                addOnSuccessListener {
                    Log.d(TAG, "Geofences added")
                }
                addOnFailureListener { e ->
                    val errorMessage = getErrorString(e)
                    Log.e(TAG, errorMessage)
                }
            }
        }
    }

    fun removeGeofences(intent: Intent) {
        val pendingIntent = getGeofencePendingIntent(intent)
        geofencingClient.removeGeofences(pendingIntent)?.run {
            addOnSuccessListener {
                Log.d(TAG, "Geofences removed")
            }
            addOnFailureListener { e ->
                val errorMessage = getErrorString(e)
                Log.e(TAG, errorMessage)
            }
        }
    }

    private fun getGeofencingRequest(geofences: List<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofences)
            println("geofencingrequest done")
        }.build()
    }

    private fun getGeofencePendingIntent(intent: Intent): PendingIntent? {
        intent.action = ACTION_GEOFENCE_EVENT
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }

    companion object {
        private const val TAG = "GeofencingHelper"
        const val ACTION_GEOFENCE_EVENT = "com.example.mobilecomputing.ACTION_GEOFENCE_EVENT"
        fun getErrorString(e: Exception): String {
            return when (e) {
                is ApiException -> {
                    when (e.statusCode) {
                        GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> "Geofence not available"
                        GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> "Too many geofences"
                        GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> "Too many pending intents"
                        GeofenceStatusCodes.GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION -> "Insufficient location permissions"
                        GeofenceStatusCodes.GEOFENCE_REQUEST_TOO_FREQUENT -> "Too frequent geofence requests"
                        else -> "Unknown geofencing error"
                    }
                }
                else -> "Unknown error"
            }
        }
    }
}