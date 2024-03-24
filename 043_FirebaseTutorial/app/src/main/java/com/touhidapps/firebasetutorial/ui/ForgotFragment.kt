package com.touhidapps.firebasetutorial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.touhidapps.firebasetutorial.databinding.FragmentForgotBinding
import com.touhidapps.firebasetutorial.databinding.FragmentLoginBinding

class ForgotFragment: Fragment() {

    private lateinit var binding: FragmentForgotBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRecover.setOnClickListener {

            val email = binding.etEmail.text.toString()

            if (email.isNullOrEmpty()) {

                binding.etEmail.error = "Enter Email"
                binding.etEmail.requestFocus()
                return@setOnClickListener

            }


            Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                      //  Log.d(TAG, "Email sent.")

                        Toast.makeText(requireActivity(), "Please check email", Toast.LENGTH_SHORT).show()

                    }
                }



        } // setOnClickListener

    }

}