package com.emi.boundservices

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.emi.threadmanagementdemo.R
import kotlinx.android.synthetic.main.activity_bound.*

class BoundActivity : AppCompatActivity() {

    private var service : BoundServices?=null
    private var isBound = false
    private var result = ArrayList<String>()

    private val connection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundServices.BoundBinder
            this@BoundActivity.service = binder.getServices()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound)
        val msg = Messages()
         msg.name = "Tanzania"
         val msg1 = msg.copy(name = "Germany")
         val msg2 = msg.copy(name = "France")
         Toast.makeText(applicationContext, "${msg.name} ${msg1.name}, ${msg2.name}", Toast.LENGTH_SHORT).show()
        result.add("Tanzania")
        result.add("Nigeria")
        result.add("France")
        result.add("Germany")
        result.add("Spain")

        val intent = Intent(this, BoundServices::class.java)
        intent.putStringArrayListExtra("result", result)
        intent.action = "start"
        val bind = bindService(intent, connection, Context.BIND_AUTO_CREATE)
        startService(intent)

        start_bound.setOnClickListener {
            isBound = true
           val data =  service?.fetchWork()?.call()
            Toast.makeText(applicationContext,"the country : ${data?.name}", Toast.LENGTH_SHORT).show()
        }

        end_bound.setOnClickListener {
            if (bind) {
                unbindService(connection)
                isBound = false
                Toast.makeText(applicationContext, "its already disconnected", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        isBound = false
        unbindService(connection)
        super.onDestroy()

    }
}
