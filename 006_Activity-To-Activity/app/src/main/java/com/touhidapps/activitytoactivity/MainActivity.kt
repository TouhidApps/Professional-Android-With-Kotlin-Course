package com.touhidapps.activitytoactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNextPage = findViewById<Button>(R.id.btnNextPage)
        btnNextPage.setOnClickListener {
            val i = Intent(this@MainActivity, SecondActivity::class.java).apply {
                putExtra("MY_NAME", "Md Touhidul Islam")
            }
            startActivity(i)
        }

    }




}