package com.touhidapps.firebasetutorial.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.touhidapps.firebasetutorial.getFirebaseToken

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)



    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        getFirebaseToken(this) {
            // send token to server using api call
            FirebaseMessaging.getInstance().subscribeToTopic("global")
//            FirebaseMessaging.getInstance().subscribeToTopic("helth")

        }

    }

}