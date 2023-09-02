package com.touhidapps.a13_serviceandnotification

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.touhidapps.a13_serviceandnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStartService.setOnClickListener {
            val i = Intent(this, MyService::class.java)
            startService(i)
        }

        binding.btnStopService.setOnClickListener {
            val i = Intent(this, MyService::class.java)
            stopService(i)
        }


    }

}