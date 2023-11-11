package com.touhidapps.multimedia

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.touhidapps.multimedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mp = MediaPlayer.create(this, R.raw.my_audio) // Offline
        mp = MediaPlayer.create(this, Uri.parse("https://touhidapps.com/api/demo/akash.mp3")) // Online
        mp.isLooping = true

        listeners()


    } // onCreate

    private fun listeners() {

        binding.btnPlay.setOnClickListener {
            mp.start()
        }

        binding.btnPause.setOnClickListener {
            mp.pause()
        }

        binding.btnStop.setOnClickListener {
            mp.seekTo(0)
            mp.pause()
        }

    } // listeners

    override fun onStop() {
        super.onStop()

        mp.release()
        mp.stop()

    }


}