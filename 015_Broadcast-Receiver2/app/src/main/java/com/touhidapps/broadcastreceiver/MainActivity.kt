package com.touhidapps.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.touhidapps.broadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val myAction = "com.touhidapps.sendMyData"
    private val dataKey = "MY_DATA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSendBroadcast.setOnClickListener {
            broadcastIntent()
        }


    }

    override fun onResume() {
        super.onResume()

        registerReceiver(myReceiver, IntentFilter(myAction))

    }

    override fun onPause() {
        super.onPause()

        try {
            unregisterReceiver(myReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // Data sender
    private fun broadcastIntent() {
        val intent = Intent().apply {
            action = myAction
            putExtra(dataKey, "This is a custom broadcast receiver!")
        }
        sendBroadcast(intent)
    }

    // Data receiver
    val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            intent?.action?.let {

                if (it == myAction) {
                    Toast.makeText(context, "Data is: ${intent?.getStringExtra(dataKey)}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }



}










