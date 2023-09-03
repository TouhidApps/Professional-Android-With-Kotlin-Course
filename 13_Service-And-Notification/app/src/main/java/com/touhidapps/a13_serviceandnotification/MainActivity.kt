package com.touhidapps.a13_serviceandnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.touhidapps.a13_serviceandnotification.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

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

            showNotificaiton()

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


    } // onCreate


    private fun showNotificaiton() {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "my_channel_id"
        val bm = (resources.getDrawable(R.mipmap.ic_launcher) as Drawable).toBitmap()
        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon(bm)
            setContentTitle("My Notificaiton")
            setContentText("Hello Notificaiton!")
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

    } // showNotificaiton

}











