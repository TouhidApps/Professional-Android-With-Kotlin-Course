package com.touhidapps.firebasetutorial.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.touhidapps.firebasetutorial.MainActivity
import com.touhidapps.firebasetutorial.R
import com.touhidapps.firebasetutorial.getFirebaseToken
import com.touhidapps.firebasetutorial.model.PushNotificationObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.random.Random

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        println("onMessageReceived: ${message.data}")

        val gson = Gson()
        val jsonString = gson.toJson(message.data)
        val pushNotificationObject: PushNotificationObject = gson.fromJson(jsonString, PushNotificationObject::class.java)

        showNotification(
            pushNotificationObject.pushTitle ?: "",
            pushNotificationObject.pushBody ?: "",
            pushNotificationObject.pushType ?: "",
            pushNotificationObject.pushImage ?: ""
        )

        println("onMessageReceived 2 : ${jsonString}")


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        getFirebaseToken(this) {
            // send token to server using api call
            FirebaseMessaging.getInstance().subscribeToTopic("global")
//            FirebaseMessaging.getInstance().subscribeToTopic("helth")

        }

    }

    private fun showNotification(mTitle: String, mBody: String, pushType: String, imgUrl: String) {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = pushType.replace(" ", "_")
//        val bm = (resources.getDrawable(R.mipmap.ic_launcher) as Drawable).toBitmap()

        val notifSmall = RemoteViews(packageName, R.layout.notificaiton_small)
        val notifBig = RemoteViews(packageName, R.layout.notificaiton_big)


        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.mipmap.ic_launcher)
//            setLargeIcon(bm)
//            setContentTitle(mTitle)
//            setContentText(mBody)
//            setStyle(NotificationCompat.BigTextStyle().bigText(mBody).setSummaryText("#Breaking News"))

            setStyle(NotificationCompat.DecoratedCustomViewStyle())
            setCustomContentView(notifSmall)
            setCustomBigContentView(notifBig)

            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        notifSmall.setTextViewText(R.id.tvTitle, mTitle)
        notifSmall.setTextViewText(R.id.tvBody, mBody)

        notifBig.setTextViewText(R.id.tvTitle, mTitle)
        notifBig.setTextViewText(R.id.tvBody, mBody)

//        applyImageUrl(builder, imgUrl)
        applyImageUrl(notifSmall, notifBig, imgUrl)

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(channelId, pushType.replace("_", " ").replace("-", " "), NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Daily tech news update"
            }
            notifManager.createNotificationChannel(mChannel)
        }
        val rn = Random.nextInt(1000, 10000)
        notifManager.notify(rn, builder.build())

    } // showNotificaiton

    fun applyImageUrl(builder: NotificationCompat.Builder, imageUrl: String) = runBlocking {

        val url = URL(imageUrl)
        withContext(Dispatchers.IO) {

            try {
                val input = url.openStream()
                BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        }?.let { bitmap ->
            builder.setLargeIcon(bitmap)
            builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
        }

    }

    fun applyImageUrl(notifSmall: RemoteViews, notifBig: RemoteViews, imageUrl: String) = runBlocking {

        val url = URL(imageUrl)
        withContext(Dispatchers.IO) {

            try {
                val input = url.openStream()
                BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        }?.let { bitmap ->
            notifSmall.setImageViewBitmap(R.id.ivThumb, bitmap)
            notifBig.setImageViewBitmap(R.id.ivThumb, bitmap)
        }

    }

}