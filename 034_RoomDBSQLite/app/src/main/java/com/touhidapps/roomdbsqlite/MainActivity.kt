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
import com.touhidapps.roomdbsqlite.db.ContactEntity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val items: ArrayList<ContactEntity> = arrayListOf()
    private val mAdapter = ContactAdapter(items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        binding.btnAddContact.setOnClickListener {

            addContactAlert(true, null)

        }

        loadData()

    } // onCreate

    private fun initUI() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setEditClick {
            addContactAlert(false, it)
        }
        mAdapter.setDeleteClick {
            showDeleteAlert(it)
        }


    } // initUI

    private fun loadData() {

        // থ্রেডের মাধ্যমে ব্যবহার
//        Thread(Runnable {
//            val allData = App.db.contactDao().getAllContact()
//            Log.d("<ALL_DATA>", "loadData: ${allData}")
//
//            items.clear()
//            items.addAll(allData)
//
//            runOnUiThread {
//                mAdapter.notifyDataSetChanged()
//            }
//
//        }).start()


        // কোরুটিনের ব্যবহার
        lifecycleScope.launch {

            val allData = App.db.contactDao().getAllContact()
            Log.d("<ALL_DATA>", "loadData: ${allData}")

            items.clear()
            items.addAll(allData)

            mAdapter.notifyDataSetChanged()

        }



    } // loadData

    private fun addContactAlert(isAdd: Boolean, contactEntity: ContactEntity?) {

        AlertDialog.Builder(this).create().apply {

            val bindingAlert = AlertInsertDataBinding.inflate(layoutInflater)
            setView(bindingAlert.root)

            if (!isAdd) {
                bindingAlert.etFirstName.setText(contactEntity?.firstName ?: "")
                bindingAlert.etLastName.setText(contactEntity?.lastName ?: "")
                bindingAlert.etPhoneNumber.setText(contactEntity?.phoneNumber ?: "")
                bindingAlert.btnSave.setText("Update")
            }

            bindingAlert.btnCancel.setOnClickListener {
                dismiss()
            }

            bindingAlert.btnSave.setOnClickListener {

                dismiss()

                val fName = bindingAlert.etFirstName.text.toString()
                val lName = bindingAlert.etLastName.text.toString()
                val pNumber = bindingAlert.etPhoneNumber.text.toString()

                if (isAdd) {
                    insertData(fName, lName, pNumber)
                } else {
                    contactEntity?.id?.let {
                        updateData(it, fName, lName, pNumber)
                    }
                }


            }

        }.show()

    } // addContactAlert

    private fun insertData(fName: String, lName: String, pNumber: String) {
//        val allData = arrayListOf(
//            ContactEntity(firstName = "Touhidul", lastName = "Islam", phoneNumber = "01786"),
//            ContactEntity(firstName = "Abc", lastName = "Def", phoneNumber = "1223")
//        )

        lifecycleScope.launch {
            App.db.contactDao().insertAll(ContactEntity(firstName = fName, lastName = lName, phoneNumber = pNumber)) // *allData.toTypedArray()
            loadData()
        }



    }

    private fun updateData(id: Int, fName: String, lName: String, pNumber: String) {

        val cont = ContactEntity(id, fName, lName, pNumber)
        lifecycleScope.launch {
            App.db.contactDao().updateData(cont)
            loadData()
        }



    } // updateData

    private fun showDeleteAlert(mContact: ContactEntity) {

        AlertDialog.Builder(this).create().apply {
            setTitle("Delete Alert!")
            setMessage("Are you sure you want to delete ${mContact.firstName} ${mContact.lastName}?")

            setButton(DialogInterface.BUTTON_NEGATIVE, "No", object : OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.dismiss()
                }
            })
            setButton(DialogInterface.BUTTON_POSITIVE, "Yes", object : OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.dismiss()

                    deleteContactData(mContact.id)

                }
            })

        }.show()

    } // showDeleteAlert

    private fun deleteContactData(id: Int) {
        val cont = ContactEntity(id = id)
        lifecycleScope.launch {
            App.db.contactDao().deleteData(cont)
            loadData()
        }

    } // deleteContactData


}