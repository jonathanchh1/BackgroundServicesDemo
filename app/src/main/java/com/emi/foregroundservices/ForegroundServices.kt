package com.emi.foregroundservices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.emi.threadmanagementdemo.R

class ForegroundServices : Service(){


    override fun onCreate() {
        super.onCreate()
        notiChannel(this)
        showNotification(null, null)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action == START && intent.hasExtra("data")){
            val data = intent.getParcelableExtra("data") as Noti
             val result = ArrayList<Noti>()
             result.add(data)
             showNotification(data.title, data.body)
        }

        return START_STICKY
    }


    fun showNotification(title : String?, msg : String?){
        val channel_id = "channel_id"
        val builder = NotificationCompat.Builder(this, channel_id)
            .setOngoing(true)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_alarm_black_24dp)
            .setContentText(msg)
            .setStyle(NotificationCompat.BigTextStyle())
            .build()
        startForeground(CHAID, builder)

    }

    private fun notiChannel(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "reminder"
            val channel_id = "channel_id"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channel_id, channelName, importance)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        stopSelf()
        stopForeground(true)
        super.onDestroy()
    }

    companion object{
        const val START = "start"
        const val CHAID = 100
    }
}