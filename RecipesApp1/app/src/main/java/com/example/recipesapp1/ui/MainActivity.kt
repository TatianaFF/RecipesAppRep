package com.example.recipesapp1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.recipesapp1.R
import com.example.recipesapp1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation()
    }

    private fun navigation(){
        navController = Navigation.findNavController(this, R.id.nav_fragment)

        binding.navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile_item -> {
                    navController.navigate(R.id.profileFragment)
                }
                R.id.main_recipes_item -> {
                    navController.navigate(R.id.recipesFragment)
                }
                R.id.settings_item -> {
                    navController.navigate(R.id.settingsFragment)
                }
            }
            true
        }
    }
}