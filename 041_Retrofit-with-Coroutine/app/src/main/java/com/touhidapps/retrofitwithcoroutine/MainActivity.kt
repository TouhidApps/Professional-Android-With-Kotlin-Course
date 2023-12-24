package com.touhidapps.retrofitwithcoroutine

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.touhidapps.retrofitwithcoroutine.databinding.ActivityMainBinding
import com.touhidapps.retrofitwithcoroutine.databinding.AlertInsertDataBinding
import com.touhidapps.retrofitwithcoroutine.model.LocationModel
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
        userAdapter.setEditClick {

            addUserAlert(false, it)

        }

        userAdapter.setDeleteClick {
            deleteAlert(it)
        }


    } // initUI

    private fun listeners() {

        binding.btnAddUser.setOnClickListener {

            addUserAlert(true, null)

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

    private fun addUserAlert(isSave: Boolean, userModel: UserModel?) {

        tempSelectedLocationIndexs.clear()

        val bindingAlert : AlertInsertDataBinding
        val locsForServer = arrayListOf<Int>()
        val oldLocIds = arrayListOf<Int>()
        AlertDialog.Builder(this).create().apply {

            bindingAlert = AlertInsertDataBinding.inflate(layoutInflater)
            setView(bindingAlert.root)

            if (!isSave) {
                bindingAlert.materialTextView.text = "Update User: ${userModel?.name}"
                bindingAlert.btnSave.setText("Update")

                bindingAlert.etName.setText("${userModel?.name ?: ""}")
                bindingAlert.etPhone.setText("${userModel?.phoneNumber ?: ""}")
                bindingAlert.etAmount.setText("${userModel?.amount ?: ""}")
                bindingAlert.etCity.setText("${userModel?.address?.city ?: ""}")
                bindingAlert.etCountry.setText("${userModel?.address?.country ?: ""}")

                var loc = ""
                userModel?.location?.forEachIndexed { index, location ->
                    if (index != 0) {
                        loc += ", "
                    }
                    loc += location.locationName ?: ""
                    location.locationId?.let {
                        oldLocIds.add(it)
                    }

                }
                bindingAlert.tvLocations.text = loc

            }

            bindingAlert.btnLocation.setOnClickListener {

                tempCheckedLocation = BooleanArray(tempAllLocations.size) // all false
                tempAllLocations.forEachIndexed { index, locationModel ->
                    locationModel.id?.toInt()?.let {
                        if (oldLocIds.contains(it)) {
                            tempCheckedLocation.set(index, true)
                        }
                    }
                }

                showLocationChooser(tempAllLocations, tempCheckedLocation,
                    selected = { s, ids ->
                       bindingAlert.tvLocations.text = s
                        locsForServer.clear()
                        locsForServer.addAll(ids)
                    }, newLocation = {
                        bindingAlert.btnLocation.performClick()
                    })

            }

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

                saveUserData(isSave, userModel?.userId, name, phone, mAmount, city, country, locsForServer.toTypedArray()) {
                    dismiss()
                }

            }

        }.show() // alert

        bindingAlert.progressIndicator.visibility = View.VISIBLE
        getLocations {
            bindingAlert.progressIndicator.visibility = View.GONE
        }

    } // addUserAlert

    private fun saveUserData(isSave: Boolean, uId: Int?, name: String, pNumber: String,
                             amount: Double, city: String, country: String, mLocs: Array<Int>, success: () -> Unit) {

        val body = HashMap<String, Any?>().apply {
            if (!isSave) {
                put("userId", uId)
            }
            put("name", name)
            put("phoneNumber", pNumber)
            put("amount", amount)
            put("city", city)
            put("country", country)
            put("locations", mLocs)
        }
        lifecycleScope.launch {

            val resp = if (isSave) {
                RetrofitClient.retrofit.saveUser(body)
            } else {
                RetrofitClient.retrofit.updateUser(body)
            }

            println("SaveOrUpdateUserResponse: ${resp.result}")
            Toast.makeText(this@MainActivity, "${resp.result}", Toast.LENGTH_SHORT).show()

            success()

            tempPageNumber = 1
            getUser(tempPageNumber)

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

    private fun deleteAlert(userModel: UserModel?) {

        AlertDialog.Builder(this).apply {

            setTitle("Delete Alert!")
            setMessage("Do you want to delete ${userModel?.name}?")

            setPositiveButton("Yes", object : OnClickListener {

                override fun onClick(p0: DialogInterface?, p1: Int) {
                    performDelete(userModel)
                }

            })

            setNegativeButton("No", object: OnClickListener {

                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            })

        }.show()

    } // deleteAlert

    private fun performDelete(userModel: UserModel?) {

        lifecycleScope.launch {

            val body = HashMap<String, Any?>().apply {
                put("userId", userModel?.userId)
            }

            val resp = RetrofitClient.retrofit.deleteUser(body)

            println("DeleteResponse ${resp.result}")
            Toast.makeText(this@MainActivity, "${resp.result}", Toast.LENGTH_SHORT).show()

            tempPageNumber = 1
            getUser(tempPageNumber)

        }

    } // performDelete


    private val tempAllLocations : ArrayList<LocationModel> = arrayListOf()
    private var tempCheckedLocation = BooleanArray(0)
    private fun getLocations(success: (List<LocationModel>) -> Unit) {

        lifecycleScope.launch {

            val resp = RetrofitClient.retrofit.getLocations()

            tempAllLocations.clear()
            tempAllLocations.addAll(resp)

            tempCheckedLocation = BooleanArray(tempAllLocations.size)

            success(resp)

        }

    } // getLocations


    private var tempSelectedLocationIndexs = arrayListOf<Int>()
    private var ci = BooleanArray(0)

    private fun showLocationChooser(
        mLocations: List<LocationModel>, checkedItems: BooleanArray,
        selected: (String, ArrayList<Int>) -> Unit, newLocation: () -> Unit
    ) {

        AlertDialog.Builder(this).apply {
            setTitle("Location you visited")
            setCancelable(false)

            val mData = arrayListOf<String>()
            mLocations.forEach {
                mData.add(it.locationName ?: "")
            }
            val la = arrayOf(*mData.toTypedArray())
            ci = checkedItems.clone() // It will make the size of array

            if (tempSelectedLocationIndexs.isNotEmpty()) {
                tempAllLocations.forEachIndexed { index, locationModel ->
                    if (tempSelectedLocationIndexs.contains(index)) {
                        ci.set(index, true)
                    } else {
                        ci.set(index, false)
                    }
                }
            }

            setMultiChoiceItems(la, ci) { dialog, mIndex, isChecked ->

            }

            setPositiveButton("OK") { p0, p1 ->

                val mCheckedItems = (p0 as AlertDialog).listView.checkedItemPositions

                tempSelectedLocationIndexs.clear()
                mCheckedItems.forEach { key, value ->
                    if (value) {
                        tempSelectedLocationIndexs.add(key)
                    }
                }

                var s = ""
                var ids = ArrayList<Int>()
                tempAllLocations.forEachIndexed { index, locationModel ->

                    if (tempSelectedLocationIndexs.contains(index)) {
                        if (s.isNotEmpty()) {
                            s += ", "
                        }
                        s += locationModel.locationName ?: ""
                        locationModel?.id?.toInt()?.let {

                            ids.add(it)

                        }

                    }


                }

                selected(s, ids)

            }

            setNegativeButton("Cancel") { p0, p1 ->

            }
            setNeutralButton("Add Location") { p0, p1 ->
                alertNewLocation {
                    newLocation()
                }
            }


        }.show()

    } // showLocationChooser


    private fun alertNewLocation(success: () -> Unit) {

        AlertDialog.Builder(this).apply {

            setTitle("Enter Location Name")
            val et = EditText(this@MainActivity)
            et.hint = "Location Name"
            setView(et)

            setPositiveButton("Save", object : OnClickListener {

                override fun onClick(p0: DialogInterface?, p1: Int) {
                    saveLocation(et.text.toString()) {
                        getLocations {
                            success()
                        }
                    }
                }

            })

            setNegativeButton("Cancel", object : OnClickListener {

                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            })

        }.show()

    } // alertNewLocation

    private fun saveLocation(locName: String, success: () -> Unit) {

        lifecycleScope.launch {

            val body = HashMap<String, Any?>().apply {
                put("locationName", locName)
            }

            val resp = RetrofitClient.retrofit.saveLocation(body)

            println("LocationResponse ${resp.result}")
            Toast.makeText(this@MainActivity, "${resp.result}", Toast.LENGTH_SHORT).show()

            success()

        }

    } // saveLocation


}