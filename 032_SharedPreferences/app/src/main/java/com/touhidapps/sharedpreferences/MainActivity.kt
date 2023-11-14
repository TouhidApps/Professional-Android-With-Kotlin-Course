package com.touhidapps.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.touhidapps.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("my_data", Context.MODE_PRIVATE)

        listeners()

    } // onCreate


    private fun listeners() {

        binding.btnSave.setOnClickListener {

            val mName = binding.etName.text.toString()
            sharedPref.edit().putString("MY_NAME", mName).apply()
            sharedPref.edit().putString("MY_ID", "000").apply()

        }

        binding.btnRead.setOnClickListener {

            val myName: String = sharedPref.getString("MY_NAME", "") ?: ""
            binding.tvResult.text = myName

        }

        binding.btnClear.setOnClickListener {
            binding.etName.text?.clear()
            binding.tvResult.text = ""

         //   sharedPref.edit().remove("MY_ID").apply()
            sharedPref.edit().clear().apply() // clear all data

        }

    }



}