package com.touhidapps.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.touhidapps.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainFragment: MainFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNext.setOnClickListener {

            val bundle = Bundle().apply {
                putString(MyConstants.MY_COUNTRY, "Bangladesh")
            }
//            mainFragment.arguments = bundle
//            supportFragmentManager
//                ?.beginTransaction()
//                ?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
//                ?.addToBackStack("MainFragment")
//                ?.add(R.id.fragmentContainer, mainFragment, "MainFragment")
//                ?.commit()

            addFragment(this, bundle, mainFragment)


        }


        binding.btnRemove.setOnClickListener {

            supportFragmentManager?.apply {
                beginTransaction()?.remove(mainFragment)?.commit()
                popBackStack()
            }

        }


    } // onCreate








}