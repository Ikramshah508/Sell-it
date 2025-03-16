package com.example.sellit

import android.app.Application
import com.google.firebase.FirebaseApp

class SellItApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}