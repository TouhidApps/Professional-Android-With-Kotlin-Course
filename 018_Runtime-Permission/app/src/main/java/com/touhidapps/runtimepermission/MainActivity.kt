package com.touhidapps.runtimepermission

import android.Manifest
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.touhidapps.runtimepermission.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val PER_REQ_CDOE = 11111

    private val myPermissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPermission.setOnClickListener {
            requestPermission()
        }



    } // onCreate

    private fun requestPermission() {

        if (isAllGranted()) {
            Toast.makeText(this, "All Granted", Toast.LENGTH_SHORT).show()
            // Code for camera & location
        } else if (shouldShowRationale()) {

            AlertDialog.Builder(this@MainActivity).apply {
                setTitle("Alert!")
                setMessage("You need to allow permission to use this feature!")
                setCancelable(false)
                setNegativeButton("Cancel", object : OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0?.dismiss()
                    }
                })
                setPositiveButton("Allow", object : OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0?.dismiss()
                        ActivityCompat.requestPermissions(this@MainActivity, myPermissions, PER_REQ_CDOE)
                    }
                })
            }.show()

        } else {
            ActivityCompat.requestPermissions(this@MainActivity, myPermissions, PER_REQ_CDOE)
        }


    } // requestPermission


    private fun isAllGranted(): Boolean {
        val p = myPermissions.filter { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
        return p.isEmpty()
    } // isAllGranted

    private fun shouldShowRationale(): Boolean {
        val p = myPermissions.filter { ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, it) }
        return p.isNotEmpty()
    } // shouldShowRationale

    private fun goToSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PER_REQ_CDOE) {
            if (grantResults.contains(PackageManager.PERMISSION_DENIED)) {
                // user denied one or more permissions
                if (!shouldShowRationale()) {
                    AlertDialog.Builder(this@MainActivity).apply {
                        setTitle("Alert!")
                        setMessage("Please go to settings and allow permissions!")
                        setCancelable(false)
                        setNegativeButton("Cancel", object : OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0?.dismiss()
                            }
                        })
                        setPositiveButton("Settings", object : OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0?.dismiss()
                                goToSettings()
                            }
                        })
                    }.show()
                }
            } else {
                requestPermission()
            }


        }


    }

}