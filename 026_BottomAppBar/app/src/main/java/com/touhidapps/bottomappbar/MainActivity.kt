package com.touhidapps.bottomappbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.touhidapps.bottomappbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "Nav Click", Toast.LENGTH_SHORT).show()
        }

        binding.bottomAppBar.setOnMenuItemClickListener {
            when(it.itemId) {

                R.id.search -> {
                    Toast.makeText(this, "Search Click", Toast.LENGTH_SHORT).show()
                }
                R.id.more -> {
                    Toast.makeText(this, "More Click", Toast.LENGTH_SHORT).show()
                }

            }
            true
        }


    }



}