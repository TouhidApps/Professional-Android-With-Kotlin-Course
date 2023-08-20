package com.touhidapps.eventlistener_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.button.MaterialButton
import com.touhidapps.eventlistener_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()/*, OnClickListener*/ {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      // var btnSecond: MaterialButton = findViewById(R.id.btnSecondClick)
        binding.btnSecondClick.setOnClickListener {
            Toast.makeText(this, "Second Click", Toast.LENGTH_SHORT).show()
        }

//        binding.btnSecondClick.setOnClickListener(object: OnClickListener {
//            override fun onClick(p0: View?) {
//                // your code
//            }
//        })

//        binding.btnFirstClick.setOnClickListener(this)
//        binding.btnSecondClick.setOnClickListener(this)


        // Long lick event
        binding.tvLongClick.setOnLongClickListener {
            Toast.makeText(this, "Long Click", Toast.LENGTH_SHORT).show()
            true
        }

        // Touch event
        binding.btnTouch.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    Toast.makeText(this, "Touch: DOWN", Toast.LENGTH_SHORT).show()
                }
                MotionEvent.ACTION_UP -> {
                    Toast.makeText(this, "Touch: UP", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }


        // TextWatcher
        binding.etWatcher.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Toast.makeText(this@MainActivity, "Text: Before Change", Toast.LENGTH_SHORT).show()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Toast.makeText(this@MainActivity, "Text: Change", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(p0: Editable?) {
                Toast.makeText(this@MainActivity, "Text: After Change", Toast.LENGTH_SHORT).show()
            }
        })



    } // onCreate

    fun firstClick(view: View) {
        Toast.makeText(this, "First Click", Toast.LENGTH_SHORT).show()
    }

//    override fun onClick(p0: View?) {
//        // your code
//        when(p0?.id) {
//            binding.btnFirstClick.id -> {
//                // first btn click
//            }
//            binding.btnSecondClick.id -> {
//                // second button click
//            }
//
//        }
//    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Key Back", Toast.LENGTH_SHORT).show()
        } else if (keyCode == KeyEvent.KEYCODE_A) {
            Toast.makeText(this, "Key A", Toast.LENGTH_SHORT).show()
        } else if (keyCode == KeyEvent.KEYCODE_B) {
            Toast.makeText(this, "Key B", Toast.LENGTH_SHORT).show()
        }

        return super.onKeyDown(keyCode, event)
    }

}