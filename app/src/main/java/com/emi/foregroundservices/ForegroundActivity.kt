package com.emi.foregroundservices

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emi.foregroundservices.ForegroundServices.Companion.START
import com.emi.threadmanagementdemo.R

class ForegroundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground)

        val data = mapOf("Text Melissa Reminder" to "Hey Bebe, How are you doing?")
        val noti = Noti()
        for((title, body) in data){
            noti.title = title
            noti.body = body
        }

        val intent = Intent(this, ForegroundServices::class.java)
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        intent.putExtra("data", noti)
        intent.action = START
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent)
        }else{
            startService(intent)
        }
    }
}
