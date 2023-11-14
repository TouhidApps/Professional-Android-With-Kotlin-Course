package com.touhidapps.multimedia


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.touhidapps.multimedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(Uri.parse("https://touhidapps.com/api/demo/pink.mp4"))
        val mediaItem2 = MediaItem.fromUri(Uri.parse("https://touhidapps.com/api/demo/kotlin_promo.mp4"))
//        player.setMediaItem(mediaItem)
        player.setMediaItems(listOf(mediaItem, mediaItem2))
        player.prepare()
        player.play()


    } // onCreate


}