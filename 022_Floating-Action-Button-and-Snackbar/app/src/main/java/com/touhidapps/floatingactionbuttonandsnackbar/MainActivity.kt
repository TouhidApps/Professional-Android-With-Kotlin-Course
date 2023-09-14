package com.touhidapps.floatingactionbuttonandsnackbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.touhidapps.floatingactionbuttonandsnackbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fab.setOnClickListener {

         //   Snackbar.make(binding.root, "Clicked on FAB", Snackbar.LENGTH_SHORT).show()

            Snackbar.make(binding.root, "This is a snackbar!", Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Dismiss", object : OnClickListener {
                    override fun onClick(p0: View?) {
                        dismiss()
                        // Your code
                    }
                })
            }.show()


        }


    }


}