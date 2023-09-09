package com.touhidapps.a17_alertdialog

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.touhidapps.a17_alertdialog.databinding.ActivityMainBinding
import com.touhidapps.a17_alertdialog.databinding.AlertCustomBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAlertDefault.setOnClickListener {
            showAlertDefault(this@MainActivity)
        }

        binding.btnAlertCustom.setOnClickListener {

            showAlertCusotm(this@MainActivity)

        }


    } // onCreate

    private fun showAlertDefault(context: Context) {

        AlertDialog.Builder(context).apply {
            setTitle("Alert!")
            setMessage("This is a default dialog!")
            setCancelable(false)
            setNegativeButton("No", object : OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    dialog?.dismiss()
                    Toast.makeText(context, "Clicked on No", Toast.LENGTH_SHORT).show()
                }
            })

            setPositiveButton("Yes", object : OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    dialog?.dismiss()
                    Toast.makeText(context, "Clicked on Yes", Toast.LENGTH_SHORT).show()
                }
            })

        }.show()

    }

    private fun showAlertCusotm(context: Context) {

        AlertDialog.Builder(context).create().apply {
//            val view = layoutInflater.inflate(R.layout.alert_custom, null)
//            setView(view)
            val bindingAlert = AlertCustomBinding.inflate(layoutInflater)
            setView(bindingAlert.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            bindingAlert.tvTitle.text = "Delete alert!"
            bindingAlert.tvDescription.text = "Are you sure to delete this item?"
            bindingAlert.btnNo.setOnClickListener {
                dismiss()
                Toast.makeText(context, "Item not deleted", Toast.LENGTH_SHORT).show()
            }
            bindingAlert.btnYes.setOnClickListener {
                dismiss()
                Toast.makeText(context, "Item deleted!", Toast.LENGTH_SHORT).show()
            }
            setCanceledOnTouchOutside(false)

        }.show()

    }

}












