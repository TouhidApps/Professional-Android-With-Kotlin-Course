package com.touhidapps.a13_serviceandnotification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService: Service() {

    private val TAG = "MyService"

    var isServiceRunning = false

    /**
     * When call with startService() & stopService()
     * onCreate -> onStartCommand -> onDestroy
     *
     */
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        isServiceRunning = true
        // Long runnign operation (Ex: Play meadia player, download, IO operation)
        var mJob: Job? = null
        mJob = CoroutineScope(Dispatchers.IO).launch {

            if (!isServiceRunning) {
                mJob?.cancel()
            }

            while (isServiceRunning) {
                Log.d(TAG, "onStartCommand: Running Loop")
                delay(1000)
            }

        }


      //  return Service.START_STICKY
        return Service.START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "onBind: ")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind: ")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
        isServiceRunning = false
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d(TAG, "onTaskRemoved: ")
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

}