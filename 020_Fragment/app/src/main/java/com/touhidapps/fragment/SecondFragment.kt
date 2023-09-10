package com.touhidapps.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.touhidapps.fragment.databinding.FragmentSecondBinding


class SecondFragment: Fragment() {

    private lateinit var binding: FragmentSecondBinding

    private var mCountry: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCountry = arguments?.getString(MyConstants.MY_COUNTRY, "") ?: ""

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set dat to view

        binding.tvMain.text = mCountry

    }

}