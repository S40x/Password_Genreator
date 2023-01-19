package com.example.password_genreator.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.password_genreator.R
import com.example.password_genreator.view.ConstObject
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFireBaseMessaging():FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        getNotificationFireBase(message.notification?.title.toString(),message.notification?.body.toString())

    }
    fun getNotificationFireBase(titel:String,description:String){
        val notification = Notification.Builder(this,ConstObject.ChanelId_FireBase)
            .setContentText(titel)
            .setContentText(description)
            .setSmallIcon(android.R.drawable.alert_light_frame)

        val notificationService = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationBuildChannel = NotificationChannel(ConstObject.ChanelId_FireBase,"Show",NotificationManager.IMPORTANCE_HIGH)
                notificationService.createNotificationChannel(notificationBuildChannel)
        }
        notificationService.notify(0,notification.build())

    }
}