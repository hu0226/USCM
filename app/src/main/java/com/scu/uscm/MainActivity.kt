package com.scu.uscm

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setUpView()
    }

    private fun setUpView() {

    }
}
