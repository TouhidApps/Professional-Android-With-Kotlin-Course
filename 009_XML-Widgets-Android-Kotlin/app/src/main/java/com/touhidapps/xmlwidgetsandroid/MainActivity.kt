package com.touhidapps.xmlwidgetsandroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.touhidapps.xmlwidgetsandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TextView
        val myTitle: String = binding.tvTitle.text.toString()
        Toast.makeText(this, myTitle, Toast.LENGTH_SHORT).show()
        // set text
        binding.tvTitle.text = "Changed Title"

        // EditText + Button
        binding.btnGetData.setOnClickListener {
            val myName: String = binding.etName.text.toString()
            Toast.makeText(this, myName, Toast.LENGTH_SHORT).show()
            binding.etName.setText("This is ${myName}")
            // button
            binding.btnGetData.text = "Done"
            binding.btnGetData.setTextColor(Color.WHITE)
            binding.btnGetData.setBackgroundColor(Color.BLUE)

            // Visibility
            binding.tvTitle.visibility = View.GONE

            // ImgeView
            binding.ivMain.setImageResource(R.drawable.baseline_person_24)

            // switch
            val isOn: Boolean = binding.switchChange.isChecked
            Toast.makeText(this, "Is On: ${isOn}", Toast.LENGTH_SHORT).show()
           // binding.switchChange.toggle()
            binding.switchChange.isChecked = true

            // check box
          //  binding.cbCheck.isChecked
            binding.cbCheck.toggle()

            // Radio button
            var rb1: Boolean = binding.rbOne.isChecked
            var rb2: Boolean = binding.rbTwo.isChecked
            Toast.makeText(this, "Radio: ${rb1} - ${rb2}", Toast.LENGTH_SHORT).show()

        }



    } // onCreate

}