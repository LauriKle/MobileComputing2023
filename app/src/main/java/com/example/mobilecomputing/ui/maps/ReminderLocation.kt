package com.example.mobilecomputing.ui.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.mobilecomputing.ui.util.rememberMapViewWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun ReminderLocation(navController: NavController) {
    val mapView: MapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    lateinit var fusedLocationClient: FusedLocationProviderClient

    AndroidView({ mapView }) { mapView ->
        coroutineScope.launch {
            val map = mapView.awaitMap()
            map.uiSettings.isZoomControlsEnabled = true
            map.uiSettings.isScrollGesturesEnabled = true
            val location = LatLng(65.06, 25.47)
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    10f
                )
            )

            val locationDataBundle = Bundle()
            setMapLongClick(map, navController, locationDataBundle)
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController,
    locationDataBundle: Bundle
) {
    map.setOnMapLongClickListener { latlng ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latlng.latitude,
            latlng.longitude
        )

        map.addMarker(
            MarkerOptions().position(latlng).title("Reminder location").snippet(snippet)
        ).apply {
            locationDataBundle.putDouble("latitude", latlng.latitude)
            locationDataBundle.putDouble("longitude", latlng.longitude)
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("location_data", locationDataBundle)
        }
    }
}