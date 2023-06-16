package com.example.journeymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.journeymate.fragments.ExplorerFragment
import com.example.journeymate.fragments.FavoritesFragment
import com.example.journeymate.fragments.MyListsFragment
import com.example.journeymate.fragments.NewRoutineFragment
import com.example.journeymate.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_bar)
        navView.setupWithNavController(navController)

    }
}