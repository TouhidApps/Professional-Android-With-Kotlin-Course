package com.touhidapps.firebasetutorial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.touhidapps.firebasetutorial.addFragment
import com.touhidapps.firebasetutorial.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToHome()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRegistraiton.setOnClickListener {

            addFragment(requireActivity(), RegistrationFragment(), null, true, false)

        }

        binding.btnLogin.setOnClickListener {

            validateLoginData()

        }

        binding.btnForgot.setOnClickListener {

            addFragment(requireActivity(), ForgotFragment(), null, true, false)


        }


    } // onViewCreated

    private fun validateLoginData() {

        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

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

        login(email, pass)

    } // validateLoginData

    private fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                 //   Log.d(TAG, "signInWithEmail:success")
                    auth.currentUser?.let {
                        goToHome()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                  //  Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireActivity(),
                        "Authentication failed.\n${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
//                    updateUI(null)
                }
            }
    }

    private fun goToHome() {
        addFragment(requireActivity(), HomeFragment(), null, false, true)
    }



}