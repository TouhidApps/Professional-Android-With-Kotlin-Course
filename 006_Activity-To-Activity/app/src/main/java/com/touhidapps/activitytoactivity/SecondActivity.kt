package com.touhidapps.activitytoactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val myName: String = intent?.getStringExtra("MY_NAME") ?: ""
        println("MyName: ${myName}")

    }



}