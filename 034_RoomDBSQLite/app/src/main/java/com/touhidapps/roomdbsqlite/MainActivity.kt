package com.touhidapps.roomdbsqlite

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.touhidapps.roomdbsqlite.databinding.ActivityMainBinding
import com.touhidapps.roomdbsqlite.databinding.AlertInsertDataBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var items: ArrayList<ContactEntity> = arrayListOf()
    private val mAdapter = ContactAdapter(items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        loadData()




        binding.btnAddContact.setOnClickListener {

            myAlertDialog(true, null)

        }


    } // onCreate

    private fun initUI() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.recyclerView.adapter = mAdapter
        mAdapter.setEditClick {
            myAlertDialog(false, it)
        }
        mAdapter.setDeleteClick {
            AlertDialog.Builder(this).create().apply {
                setTitle("Delete Alert!")
                setMessage("Are you sure you want to delete ${it?.firstName} ${it?.lastName}?")
                setButton(DialogInterface.BUTTON_NEGATIVE, "No", object : OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0?.dismiss()
                    }
                })
                setButton(DialogInterface.BUTTON_POSITIVE, "Yes", object : OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0?.dismiss()
                        it?.id?.let {
                            updateMyData(it) // যদি ফাংশনে নাম পরিবর্তন করেন: deleteMyData(id: Int)
                        }
                    }
                })
            }.show()
        }

    } // initUI

    private fun loadData() {

        // থ্রেডের ব্যবহার
//        Thread(Runnable {
//            val allData = App.db.contactDao().getAllContact()
//            Log.d("<All_data>", "onCreate: ${allData}")
//
//            items.clear()
//            items.addAll(allData)
//            runOnUiThread {
//                mAdapter.notifyDataSetChanged()
//            }
//
//        }).start()

        // কো-রুটিনের ব্যবহার

        lifecycleScope.launch {
            val allData = App.db.contactDao().getAllContact()
            Log.d("<All_data>", "onCreate: ${allData}")

            items.clear()
            items.addAll(allData)
            mAdapter.notifyDataSetChanged()
        }


    }


    private fun myAlertDialog(isAdd: Boolean, contactEntity: ContactEntity?) {

        AlertDialog.Builder(this).create().apply {

            val bindingAlert = AlertInsertDataBinding.inflate(layoutInflater)
            setView(bindingAlert.root)
//            bindingAlert.tvTile

            if (!isAdd) {
                bindingAlert.etFirstName.setText(contactEntity?.firstName ?: "")
                bindingAlert.etLastName.setText(contactEntity?.lastName ?: "")
                bindingAlert.etPhoneNumber.setText(contactEntity?.phoneNumber ?: "")
                bindingAlert.btnInsert.setText("Update")
            }

            bindingAlert.btnCancel.setOnClickListener {
                dismiss()
            }

            bindingAlert.btnInsert.setOnClickListener {

                dismiss()

                val firstName = bindingAlert.etFirstName.text.toString()
                val lastName = bindingAlert.etLastName.text.toString()
                val phoneNumber = bindingAlert.etPhoneNumber.text.toString()

                if (isAdd) {
                    insertMyData(firstName, lastName, phoneNumber)
                } else {
                    contactEntity?.id?.let {
                        updateMyData(it, firstName, lastName, phoneNumber)
                    }
                }


            } // btnInsert

        }.show() // AlertDialog

    }

    private fun insertMyData(fName: String, lName: String, phone: String) {

        lifecycleScope.launch {
            App.db.contactDao().insertAll(ContactEntity(firstName = fName, lastName = lName, phoneNumber = phone))

            loadData()
        }

    }

    private fun updateMyData(id: Int, fName: String, lName: String, phone: String) {

        lifecycleScope.launch {
            val cont = ContactEntity(id = id, firstName = fName, lastName = lName, phoneNumber = phone)
            App.db.contactDao().updateContact(cont)

            loadData()
        }


    }

    private fun updateMyData(id: Int) { // এই ফাংশনের নাম দিতে পারেন deleteMyData(id: Int)

        lifecycleScope.launch {
            val cont = ContactEntity(id = id)
            App.db.contactDao().deleteContact(cont)

            loadData()
        }

    }


}