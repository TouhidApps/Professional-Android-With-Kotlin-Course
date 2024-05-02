package com.touhidapps.a13_serviceandnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.touhidapps.a13_serviceandnotification.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStartService.setOnClickListener {
            val i = Intent(this, MyService::class.java)
            startService(i)
        }

        binding.btnStopService.setOnClickListener {
            val i = Intent(this, MyService::class.java)
            stopService(i)
        }

        binding.btnNotificaiton.setOnClickListener {

            showNotification()

        }

        binding.btnStartServiceForground.setOnClickListener {

            val i = Intent(this, MyServiceForground::class.java)
            i.putExtra("MY_NAME", "Touhid")
            startForegroundService(i)

        }

        binding.btnStopServiceForground.setOnClickListener {
            val i = Intent(this, MyServiceForground::class.java)
            stopService(i)
        }

        // Bound service
        binding.btnStartBound.setOnClickListener {
            val i = Intent(this, MyServiceBound::class.java)
            startService(i)
            bindService(i, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        binding.btnStopBound.setOnClickListener {
            val i = Intent(this, MyServiceBound::class.java)
            stopService(i)
            unbindService(serviceConnection)
        }
        binding.switchIsNumber.setOnCheckedChangeListener { compoundButton, b ->
            myServiceBound?.printRandomNumber(b)
        }

    } // onCreate

    var myBinder: MyServiceBound.MyBinder? = null
    var myServiceBound: MyServiceBound? = null

    val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            myBinder = p1 as MyServiceBound.MyBinder
            myServiceBound = myBinder?.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }


    private fun showNotification() {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "my_channel_id"
        val bm = (resources.getDrawable(R.mipmap.ic_launcher) as Drawable).toBitmap()
        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon(bm)
            setContentTitle("My Notification")
            setContentText("Hello Notification!")
            setStyle(NotificationCompat.BigTextStyle().bigText("This is big text This is big text This is big text This is big text ").setSummaryText("#Breaking News"))
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(channelId, "Tech News", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Daily tech news update"
            }
            notifManager.createNotificationChannel(mChannel)
        }
        val rn = Random.nextInt(1000, 10000)
        notifManager.notify(rn, builder.build())

//        add below permission in manifest
//         <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

    } // showNotificaiton

}












