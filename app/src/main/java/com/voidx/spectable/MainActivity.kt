package com.voidx.spectable

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import com.voidx.spectable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        WindowInsetsControllerCompat(window, binding.root)
                .hide(WindowInsetsCompat.Type.systemBars())
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.navHostFragment.findNavController()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
