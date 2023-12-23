package com.touhidapps.retrofitwithcoroutine

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.touhidapps.retrofitwithcoroutine.databinding.ActivityMainBinding
import com.touhidapps.retrofitwithcoroutine.databinding.AlertInsertDataBinding
import com.touhidapps.retrofitwithcoroutine.model.UserModel
import com.touhidapps.retrofitwithcoroutine.networkService.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val users: ArrayList<UserModel> = arrayListOf()
    private val userAdapter = UserAdapter(users)

    private val PER_PAGE_DATA = 10
    private var tempPageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        listeners()




    } // onCreate

    private fun initUI() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = userAdapter
        userAdapter.setItemClick {

            val i = Intent(this, DetailUserActivity::class.java)
            i.putExtra("USER_DATA", it)
            startActivity(i)

        }


    } // initUI

    private fun listeners() {

        binding.btnAddUser.setOnClickListener {

            addUserAlert()

        }

        binding.swipeRefresh.post {
            getUser(tempPageNumber)
        }

        binding.swipeRefresh.setOnRefreshListener {

            tempPageNumber = 1
            getUser(tempPageNumber)

        }

        binding.recyclerView.addOnScrollListener(object: OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {

                    recyclerView.layoutManager?.let {

                        val lm = it as LinearLayoutManager
                        val totalItemCount = lm.itemCount

                        if (!binding.swipeRefresh.isRefreshing &&
                            totalItemCount == lm.findLastVisibleItemPosition() + 1 &&
                            (totalItemCount % PER_PAGE_DATA) == 0
                            ) {

                            tempPageNumber++
                            getUser(tempPageNumber)

                        }

                    }

                }

            }

        })

    } // listeners

    private fun addUserAlert() {

        AlertDialog.Builder(this).create().apply {

            val bindingAlert = AlertInsertDataBinding.inflate(layoutInflater)
            setView(bindingAlert.root)

            bindingAlert.btnCancel.setOnClickListener {
                dismiss()
            }

            bindingAlert.btnSave.setOnClickListener {

                val name = bindingAlert.etName.text.toString()
                if (name.isEmpty()) {
                    bindingAlert.etName.error = "Enter Name"
                    return@setOnClickListener
                }
                val phone = bindingAlert.etPhone.text.toString()
                val amount = bindingAlert.etAmount.text.toString()
                var mAmount: Double = 0.0
                if (amount.isNotEmpty()) {
                    mAmount = amount.toDouble()
                }
                val city = bindingAlert.etCity.text.toString()
                val country = bindingAlert.etCountry.text.toString()

                saveUserData(name, phone, mAmount, city, country) {
                    dismiss()
                }

            }

        }.show() // alert

    } // addUserAlert

    private fun saveUserData(name: String, pNumber: String,
                             amount: Double, city: String, country: String, success: () -> Unit) {

        val body = HashMap<String, Any?>().apply {
            put("name", name)
            put("phoneNumber", pNumber)
            put("amount", amount)
            put("city", city)
            put("country", country)
            put("locations", arrayOf(16,17))
        }
        lifecycleScope.launch {

            val resp = RetrofitClient.retrofit.saveUser(body)
            println("SaveUserResponse: ${resp.result}")
            Toast.makeText(this@MainActivity, "${resp.result}", Toast.LENGTH_SHORT).show()

            success()

        }


    } // saveUserData

    private fun getUser(tempPageNumber: Int) {

        binding.swipeRefresh.isRefreshing = true

        lifecycleScope.launch {

            val resp = RetrofitClient.retrofit.getUser(tempPageNumber, PER_PAGE_DATA)
            println("GetUserResponse: ${resp}")

            binding.swipeRefresh.isRefreshing = false

            if (tempPageNumber == 1) {
                users.clear()
            }
            users.addAll(resp)
            userAdapter.notifyDataSetChanged()

        }

    } // getUser


}