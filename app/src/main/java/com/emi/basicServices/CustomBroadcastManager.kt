package com.emi.basicServices

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import java.util.ArrayList
import java.util.logging.Handler

class CustomBroadcastManager : BroadcastReceiver(){


    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "message" || intent?.hasExtra("name")!!){
            val data  = intent.getStringArrayListExtra("name")

            val serviceIntent = Intent(context, CustomIntentServices::class.java)
              serviceIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
              serviceIntent.putStringArrayListExtra("name", data)
              serviceIntent.action = "action"
               context?.startService(serviceIntent)
          //  Log.d(CustomBroadcastManager::class.java.simpleName, "$data all data")

        }
    }
}