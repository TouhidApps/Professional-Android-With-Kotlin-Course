package com.touhidapps.manifestandresources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.touhidapps.manifestandresources.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)


      //  binding.ivMain.setImageResource(R.drawable.me)
        binding.ivMain.setImageResource(R.mipmap.ic_launcher)

        binding.tvName.setBackgroundColor(getColor(R.color.my_color_gray))
        binding.tvName.text = getString(R.string.my_name)


      //  R.drawable.me
     //   R.mipmap.ic_launcher

       // getColor(R.color.my_color_gray)

    //   var myName: String = getString(R.string.my_name)

      //  R.raw.kotlin_promo

     //  application.assets.open("json/abc.json")
     //   "file:///android_asset/json/abc.json"

    }


}