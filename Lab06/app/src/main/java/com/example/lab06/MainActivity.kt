package com.example.lab06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var topAppBar : MaterialToolbar
    lateinit var drawerLayout : DrawerLayout
    lateinit var navigationView : NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initializeView(){
        topAppBar = findViewById(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
    }
    private fun initMenu(){
        topAppBar.setNavigationOnClickListener{
            drawerLayout.open()
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            when(menuItem.itemId) {
                R.id.home -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.home2)
                R.id.quiz -> Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.quiz)
            }
            drawerLayout.close()
            true
        }

    }
}
