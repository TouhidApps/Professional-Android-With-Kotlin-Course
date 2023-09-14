package com.touhidapps.bottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.touhidapps.bottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigation.removeBadge(R.id.bottom_navigation)
        binding.bottomNavigation.setOnItemSelectedListener {
            Toast.makeText(this, "Item: ${it.title}", Toast.LENGTH_SHORT).show()
            true
        }




    }



}