package com.touhidapps.firebasetutorial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.touhidapps.firebasetutorial.addFragment
import com.touhidapps.firebasetutorial.databinding.FragmentLoginBinding
import com.touhidapps.firebasetutorial.databinding.FragmentRegistrationBinding

class RegistrationFragment: Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRegistraiton.setOnClickListener {

            validateRegistrationData()

        }



    }

    private fun validateRegistrationData() {

        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        val confPass = binding.etPasswordConfirm.text.toString()

        if (email.isNullOrEmpty()) {

            binding.etEmail.error = "Enter Email"
            binding.etEmail.requestFocus()
            return

        }

        if (pass.isNullOrEmpty()) {

            binding.etPassword.error = "Enter Password"
            binding.etPassword.requestFocus()
            return

        }
        if (confPass.isNullOrEmpty()) {

            binding.etPasswordConfirm.error = "Enter Confirm Password"
            binding.etPasswordConfirm.requestFocus()
            return

        }

        if (pass != confPass) {
            binding.etPasswordConfirm.error = "Password did not match"
            binding.etPasswordConfirm.requestFocus()
            return
        }

        registerUser(email, pass)

    } // validateRegistrationData

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                   // Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                  //  Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireActivity(),
                        "Registration failed.\n${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    } // registerUser

    private fun updateUI(user: FirebaseUser?) {

        user?.let {

            addFragment(requireActivity(), HomeFragment())

        }

    } // updateUI


}