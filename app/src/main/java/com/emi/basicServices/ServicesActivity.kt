package com.emi.basicServices

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emi.threadmanagementdemo.R
import kotlinx.android.synthetic.main.activity_services.*

class ServicesActivity : AppCompatActivity() {

    private var receiver = CustomBroadcastManager()
    private var result = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        registerReceiver(receiver, IntentFilter("message"))

        test_broadcast.setOnClickListener {
            onBeginServices()
        }

        result.add("Nigeria")
        result.add("South Africa")
        result.add("Ethiopia")
        result.add("Zimbabwe")
    }


    private fun onBeginServices(){
        val intent = Intent(this, CustomBroadcastManager::class.java)
        intent.action = "action"
        intent.putStringArrayListExtra("name", result)
        sendBroadcast(intent)
    }

    override fun onStop() {
        unregisterReceiver(receiver)
        super.onStop()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
