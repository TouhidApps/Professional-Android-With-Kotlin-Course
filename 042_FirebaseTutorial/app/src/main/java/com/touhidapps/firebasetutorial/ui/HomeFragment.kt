package com.touhidapps.firebasetutorial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.touhidapps.firebasetutorial.addFragment
import com.touhidapps.firebasetutorial.databinding.FragmentHomeBinding
import com.touhidapps.firebasetutorial.databinding.FragmentLoginBinding

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showUserData()

        binding.btnLogout.setOnClickListener {

            // TODO alert: YES -> logout
            Firebase.auth.signOut()
            addFragment(requireActivity(), LoginFragment(), null, false, true)

        }

        binding.btnDeleteUser.setOnClickListener {
            // TODO alert: YES -> delete
            Firebase.auth.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                      //  Log.d(TAG, "User account deleted.")
                        Toast.makeText(requireActivity(), "User deleted", Toast.LENGTH_SHORT).show()
                        addFragment(requireActivity(), LoginFragment(), null, false, true)

                    }
                }

        }

    }

    private fun showUserData() {

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName ?: ""
            val email = it.email ?: ""
            val photoUrl = it.photoUrl ?: ""

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified ?: ""

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid ?: ""

            binding.tvSuccess.text = "Login Success\n${name}\n${email}\n${photoUrl}\n${emailVerified}\n${uid}"

        }

    }


}