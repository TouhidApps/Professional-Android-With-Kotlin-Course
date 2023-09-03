package com.touhidapps.intentproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.touhidapps.intentproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()

    } // onCreate

    private fun listeners() {

        /**
         * Inplicit Intent
         */
        binding.btnDial.setOnClickListener {

            val intent: Intent = Intent(Intent.ACTION_DIAL).apply {
                setData(Uri.parse("tel:0123"))
               // data = Uri.parse("tel:0123") // same as setData()
            }
            startActivity(intent)

        }

        binding.btnLocation.setOnClickListener {

            // 3.1577109207619145, 101.71150834457652

            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                setData(Uri.parse("geo:3.1577109207619145, 101.71150834457652"))
            }
            startActivity(intent)

        }

        /**
         * Explicit Intent
         */

        binding.btnStartActivity.setOnClickListener {

            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)

            // service
//            startService(Intent())
            // Broadcast Receiver
//            sendBroadcast(Intent())

        }

    } // listeners


}