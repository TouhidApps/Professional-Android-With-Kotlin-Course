package com.touhidapps.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.touhidapps.fragment.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    private val TAG = "MainFragment"
    private lateinit var binding: FragmentMainBinding
    private var mCountryName: String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")

        mCountryName = arguments?.getString(MyConstants.MY_COUNTRY, "") ?: ""

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView: ")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: ")

        binding.materialTextView.text = mCountryName


        // Set dat to view
        binding.btnNext.setOnClickListener {

            val bundle = Bundle().apply {
                putString(MyConstants.MY_COUNTRY, "${mCountryName} ${binding.etCountry.text.toString()}")
            }
            val sFragment = SecondFragment()
//                .apply {
//                arguments = bundle
//            }

//            activity?.supportFragmentManager
//                ?.beginTransaction()
//                ?.addToBackStack("SecondFragment")
//                ?.add(R.id.fragmentContainer, sFragment, "SecondFragment")
//                ?.commit()

            addFragment(activity, bundle, sFragment)


        }


    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: ")
    }

}