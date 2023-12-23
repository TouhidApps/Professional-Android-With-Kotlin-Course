package com.touhidapps.retrofitwithcoroutine

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.touhidapps.retrofitwithcoroutine.databinding.ActivityDetailUserBinding
import com.touhidapps.retrofitwithcoroutine.databinding.ActivityMainBinding
import com.touhidapps.retrofitwithcoroutine.model.UserModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mUser = intent?.getParcelableExtra("USER_DATA") as UserModel?

        mUser?.let {

            binding.tvName.text = "Name: ${it.name}"
            binding.tvPhone.text = "Phone: ${it.phoneNumber}"
            binding.tvAmount.text = "Amount: ${it.amount}"
            binding.tvAddress.text = "Address: ${it.address?.city ?: ""}, ${it.address?.country ?: ""}"

            var allLocations = ""
            it.location.forEachIndexed { index, location ->
                if (index != 0) {
                    allLocations += ", "
                }
                allLocations += location?.locationName ?: ""
            }

            binding.tvLocations.text = "Visited Locations: ${allLocations}"

        }


    }


}