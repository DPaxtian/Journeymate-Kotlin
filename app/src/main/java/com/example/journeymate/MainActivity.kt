package com.example.journeymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.example.journeymate.fragments.ExplorerFragment
import com.example.journeymate.fragments.FavoritesFragment
import com.example.journeymate.fragments.LoginFragment
import com.example.journeymate.fragments.MyListsFragment
import com.example.journeymate.fragments.NewRoutineFragment
import com.example.journeymate.fragments.ProfileFragment
import com.example.journeymate.viewmodels.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    lateinit var navigation : BottomNavigationView
    private val OnNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId){
            R.id.item_explorer -> {
                supportFragmentManager.commit {
                    replace<ExplorerFragment>(R.id.MainFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

        when (item.itemId){
            R.id.item_mylists -> {
                supportFragmentManager.commit {
                    replace<MyListsFragment>(R.id.MainFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

        when (item.itemId){
            R.id.item_newroutine -> {
                supportFragmentManager.commit {
                    replace<NewRoutineFragment>(R.id.MainFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

        when (item.itemId){
            R.id.item_favorites -> {
                supportFragmentManager.commit {
                    replace<FavoritesFragment>(R.id.MainFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

        when (item.itemId){
            R.id.item_profile -> {
                supportFragmentManager.commit {
                    replace<LoginFragment>(R.id.MainFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation = findViewById(R.id.nav_bar)
        navigation.setOnNavigationItemSelectedListener(OnNavMenu)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        supportFragmentManager.commit {
            replace<ExplorerFragment>(R.id.MainFrame)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }
    }
}