package com.touhidapps.ndk_tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.getkeepsafe.relinker.ReLinker
import com.touhidapps.ndk_tutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    companion object {
//        // Used to load the 'ndk_tutorial' library on application startup.
//        init {
////            System.loadLibrary()
//
//
//
//        }
//    }

    /**
     * A native method that is implemented by the 'ndk_tutorial' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun getMyApiKey(): String


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI() + "\n" + getMyApiKey()
    }




}