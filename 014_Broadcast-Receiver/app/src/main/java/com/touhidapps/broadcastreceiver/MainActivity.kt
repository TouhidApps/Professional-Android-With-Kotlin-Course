package com.touhidapps.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.touhidapps.broadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val receiver: BroadcastReceiver = MyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onResume() {
        super.onResume()

        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

    }

    override fun onPause() {
        super.onPause()

        try {
            unregisterReceiver(receiver)
        } catch (e: Exception) { e.printStackTrace() }


    }






}