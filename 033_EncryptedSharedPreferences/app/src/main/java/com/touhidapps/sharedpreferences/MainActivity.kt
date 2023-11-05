package com.touhidapps.sharedpreferences

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.touhidapps.sharedpreferences.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPref = EncryptedSharedPreferences.create(
            this,
            "my_data_encrypted",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

     //   sharedPref = getSharedPreferences("my_data", Context.MODE_PRIVATE)

        listeners()

    } // onCreate

    private fun listeners() {

        binding.btnSave.setOnClickListener {
            val mName: String = binding.etName.text.toString()
            sharedPref.edit().putString("MY_NAME", mName).apply()
        }
        binding.btnRead.setOnClickListener {
            val myName: String = sharedPref.getString("MY_NAME", "") ?: ""
            binding.tvResult.text = myName
        }
        binding.btnClear.setOnClickListener {
            binding.etName.text?.clear()
            binding.tvResult.text = ""
          //  sharedPref.edit().remove("MY_NAME").apply() // clear single data
            sharedPref.edit().clear().apply() // clear all data

        }


    } // listeners


}