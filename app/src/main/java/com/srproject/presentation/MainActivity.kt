package com.srproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.srproject.R

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        Navigation.findNavController(
            this@MainActivity,
            R.id.nav_host_fragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this, navController, null)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        ).navigateUp() || super.onSupportNavigateUp()
    }
}
