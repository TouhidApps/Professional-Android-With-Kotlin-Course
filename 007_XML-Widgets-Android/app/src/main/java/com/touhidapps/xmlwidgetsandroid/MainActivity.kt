package com.touhidapps.xmlwidgetsandroid

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.touhidapps.xmlwidgetsandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // findViewById() - 1
//    private lateinit var tvName: TextView

    // ViewBinding - 1
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    setContentView(R.layout.activity_main)
        // ViewBinding - 2
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // findViewById() - 2
//        tvName = findViewById(R.id.tvName)
      //  tvName.setText("Kotlin")
//        tvName.text = "Kotlin"

        // ViewBinding - 3
//        binding.tvName.text = "Android Studio"
//        binding.tvTitle.visibility = View.GONE

        // EditText - user after button click
      //  val userName: String = binding.etUserName.text.toString()
        binding.etUserName.setText("Bangladesh")
        binding.etUserName.setHint("Type your name")
        binding.etUserName.setHintTextColor(Color.CYAN)
        binding.etUserName.setTextColor(Color.GRAY)


    }

    fun showMyToast(view: View) {
       // Toast.makeText(this, "Hi hello", Toast.LENGTH_SHORT).show()

        Snackbar.make(view, "This is a snack", Snackbar.LENGTH_INDEFINITE).apply {
            setAction("Click me") {
                Toast.makeText(this@MainActivity, "Hi hello 2", Toast.LENGTH_SHORT).show()
            }
        }.show()


//        val s = Snackbar.make(view, "This is a snack", Snackbar.LENGTH_INDEFINITE)
//        s.setAction("Do it") {
//
//        }
//        s.show()

    }

    fun goToNextActivity(view: View) {

        startActivity(Intent(this, SecondActivity::class.java))

    }

}