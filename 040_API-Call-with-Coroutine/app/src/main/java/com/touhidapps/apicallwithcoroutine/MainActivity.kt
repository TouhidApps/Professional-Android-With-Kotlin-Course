package com.touhidapps.apicallwithcoroutine

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.touhidapps.apicallwithcoroutine.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLoadData.setOnClickListener {

            if (binding.progressIndicator.isVisible) {
                return@setOnClickListener
            }

            binding.progressIndicator.visibility = View.VISIBLE

            val apiPath = "https://touhidapps.com/api/v1/user.php?pageNo=1&perPageData=20"
            val method = "GET"

            MyApiClient.callMyApi(apiPath, method) {

                try {
                    val arrList: ArrayList<String> = arrayListOf()
                    val arr = JSONArray(it)
                    for (i in 0..arr.length()-1) {
                        val jsonObj : JSONObject = arr.get(i) as JSONObject

                        var city = ""
                        try {
                            val add = jsonObj.get("address") as JSONObject
                            city = add.getString("city")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        val data = "${jsonObj.getString("name")} -- ${city}"
                        arrList.add(data)
                    }

                    runOnUiThread {
                        binding.progressIndicator.visibility = View.GONE
                        val arrAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrList)
                        binding.listView.adapter = arrAdapter

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }



            }

        }


    } // onCreate




}