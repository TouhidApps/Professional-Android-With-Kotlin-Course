package com.touhidapps.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.touhidapps.navigationdrawer.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private val TAG = "MainActivityDrawer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        title = "My Drawer Example"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
       // supportActionBar?.setHomeAsUpIndicator(R.drawable.my_icon)

        binding.drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                Log.d(TAG, "onDrawerSlide")
            }

            override fun onDrawerOpened(drawerView: View) {
                Log.d(TAG, "onDrawerOpened: ")
            }

            override fun onDrawerClosed(drawerView: View) {
                Log.d(TAG, "onDrawerClosed: ")
            }

            override fun onDrawerStateChanged(newState: Int) {
                Log.d(TAG, "onDrawerStateChanged: ")
            }
        })

        mDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar, 0, 0)
        mDrawerToggle.isDrawerIndicatorEnabled = true
        mDrawerToggle.syncState()

    }

}