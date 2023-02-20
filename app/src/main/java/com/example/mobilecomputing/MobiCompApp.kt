package com.example.mobilecomputing

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobiCompApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}


