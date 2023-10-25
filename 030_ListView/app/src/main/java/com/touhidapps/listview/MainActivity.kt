package com.touhidapps.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.touhidapps.listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countries: Array<String> = arrayOf("Bangladesh", "Iran", "Turkey", "Nepal", "Thailan", "Myanmar", "Japan", "Malaysia", "Korea", "Sri Lanka")

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)

        binding.listView.adapter = arrayAdapter

        binding.listView.setOnItemClickListener { adapterView, view, i, l ->

            Toast.makeText(this, "$i--${countries[i]}", Toast.LENGTH_SHORT).show()

        }


    } // onCreate


}