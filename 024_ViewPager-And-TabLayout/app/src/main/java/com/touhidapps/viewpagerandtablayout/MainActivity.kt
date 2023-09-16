package com.touhidapps.viewpagerandtablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.touhidapps.viewpagerandtablayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2
        val fragments = listOf(FirstFragment(), SecondFragment())
        val adapter = ViewPagerAdapter(fragments, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        // Tab Layout
        val fragNames = arrayOf("First", "Second")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(fragNames[position])
        }.attach()



    }



}