package com.touhidapps.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast

class MyReceiver: BroadcastReceiver() {

    private val TAG = "MyReceiver"

    override fun onReceive(context: Context, intent: Intent?) {

        intent?.action?.let {
            when(it) {
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    val isAirplaneMode: Boolean = isAirplaneModeOn(context)
                    Log.d(TAG, "onReceive: ${isAirplaneMode}")
                    Toast.makeText(context, "Airplane: ${isAirplaneMode}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(context.contentResolver, Settings.System.AIRPLANE_MODE_ON, 0) != 0
    }


}