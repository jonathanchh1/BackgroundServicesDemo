package com.emi.basicServices

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.emi.threadmanagementdemo.MainActivity
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CustomIntentServices : IntentService("action"){

    private var executor : ExecutorService = Executors.newSingleThreadExecutor()
    private var handler = Handler(Looper.getMainLooper())

    override fun onHandleIntent(intent: Intent?) {
        if(intent?.hasExtra("name")!! && intent.action == "action"){

            val data = intent.getStringArrayListExtra("name")
            val fetch = executor.execute(runnable(data))
           // val fetch = fetchingWork(data).call()
          //  Log.d(CustomIntentServices::class.java.simpleName, "$fetch an index value")

        }
    }


    private fun runnable(list : MutableList<String>?) : Runnable{
        return Runnable {
            val rn = Random()
            val index = rn.nextInt(4)
            val data = list?.get(index)
            onProcessed(data!!)
        }
    }


    private fun onProcessed(value : String){
        handler.post {
            Log.d(CustomIntentServices::class.java.simpleName, "$value data in index")
        }
    }



    private fun fetchingWork(list : MutableList<String>?) : Callable<String?> {
        return Callable {
            val rn = Random()
            val index = rn.nextInt(4)
            return@Callable list?.get(index)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdownNow()
        executor.isShutdown
        handler.removeCallbacksAndMessages(null)
    }
}