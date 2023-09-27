package com.touhidapps.drivingtestquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.touhidapps.drivingtestquiz.databinding.ActivityMainBinding
import com.touhidapps.drivingtestquiz.ui.AboutFragment
import com.touhidapps.drivingtestquiz.ui.QuizFragment
import com.touhidapps.drivingtestquiz.ui.StartFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        addFragment(StartFragment(), this)

        binding.bottomNav.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.menuQuiz -> {
                    addFragment(StartFragment(), this)
                }

                R.id.menuAbout -> {
                    addFragment(AboutFragment(), this)
                }

                else -> {}

            }

            true

        }


    }



}