package com.emi.boundservices

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*
import java.util.concurrent.Callable
import kotlin.collections.ArrayList

class BoundServices : Service(){


    private val dataResult : ArrayList<Messages>? = ArrayList()
    inner class BoundBinder : Binder() {
        fun getServices() : BoundServices = this@BoundServices
    }

    private val binder = BoundBinder()

    override fun onBind(intent: Intent?): IBinder? {
        if(intent?.action == "start") {
            val result = intent.getStringArrayListExtra("result")!!
            result.mapNotNull {
                val msg = Messages()
                 msg.name = it
                dataResult?.add(msg)
            }
        }
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        val data = intent?.getStringExtra("terminate")
        Log.d(BoundServices::class.java.simpleName, "$data its been terminated")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    fun fetchWork() : Callable<Messages?>{
      return Callable {
          val rn = Random()
          val index = rn.nextInt(5)
          return@Callable dataResult?.get(index)
      }
    }
}