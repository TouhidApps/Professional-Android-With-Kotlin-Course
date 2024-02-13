package com.touhidapps.viewadvanced

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textview.MaterialTextView
import com.touhidapps.viewadvanced.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * View without xml
         */

//        val tv1 = MaterialTextView(this).apply {
//            text = "Hi hello"
//            textSize = 12F
//        }
//
//        val tv2 = MaterialTextView(this).apply {
//            text = "Hi test text 2"
//            textSize = 22F
//        }
//
//        val llMain = LinearLayout(this).apply {
//
//            orientation = LinearLayout.VERTICAL
//            addView(tv1)
//            addView(tv2)
//
//        }
//
//        setContentView(llMain)



        /**
         * Set font to text view
         */
     //   val tf = ResourcesCompat.getFont(this, R.font.caveat_regular)
    //    binding.tvTitle.typeface = tf


        /**
         * Animation
         */
        binding.btnAnimate.setOnClickListener {

            binding.tvTitle.animate().apply {

                translationY(0F)
                translationYBy(100F)
                translationX(0F)
                translationXBy(100F)

                duration = 1000
                setListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)

                        // Your code here

                    }
                })

            }

        }

        /**
         * Include example
         */
        binding.includeFooterButton.btnNext.setOnClickListener {


        }

        binding.includeFooterButton.btnCancel.setOnClickListener {



        }

        /**
         * Activity result
         */
        binding.btnGoNext.setOnClickListener {

            val i = Intent(this, SecondActivity::class.java)
            i.putExtra("REQUEST_CODE", 1000) // For new style
            resultLauncher.launch(i) // New style


         //  startActivityForResult(i, 1000) // Old Style


        }


        datePicker()

    } // onCreate


    private fun datePicker() {

        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
             binding.btnDatePicker.text = "${dayOfMonth}/${monthOfYear}/${year}"
        }, mYear, mMonth, mDay)

        binding.btnDatePicker.setOnClickListener {

            dpd.show()

        }

    } // datePicker



    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val allData = "Req. Code: ${result?.data?.getIntExtra("REQUEST_CODE", 0)}, Res. Code: ${result.resultCode}, Action: ${result?.data?.action}, Name: ${result?.data?.getStringExtra("MY_NAME")}"
        println(allData)

    }



    // Old style
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        val allData = "Req. Code: ${requestCode}, Res. Code: ${resultCode}, Action: ${data?.action}, Name: ${data?.getStringExtra("MY_NAME")}"
//        println(allData)
//
//    }

}