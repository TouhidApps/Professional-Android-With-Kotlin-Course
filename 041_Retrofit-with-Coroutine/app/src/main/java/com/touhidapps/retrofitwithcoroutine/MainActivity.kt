package com.touhidapps.retrofitwithcoroutine

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.os.Handler
import android.util.SparseBooleanArray
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.touhidapps.retrofitwithcoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    } // onCreate




}