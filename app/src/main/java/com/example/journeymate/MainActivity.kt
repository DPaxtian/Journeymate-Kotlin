package com.example.journeymate

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.journeymate.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var userLogged : User? = null

    companion object{
        lateinit var instance:MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.statusBarColor = Color.BLACK
        val navController = this.findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_bar)
        navView.setupWithNavController(navController)
        instance = this
    }
}