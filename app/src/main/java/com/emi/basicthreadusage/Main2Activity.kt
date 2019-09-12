package com.emi.basicthreadusage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.emi.threadmanagementdemo.R
import java.util.*
import java.util.concurrent.*

class Main2Activity : AppCompatActivity() {

   private val executor : ExecutorService = Executors.newSingleThreadExecutor()
   private val countries = mutableListOf<String>()
   private var UiHandler = Handler()
   val runnable = Runnable {
       countries.add("Kenya")
       countries.add("Tanzania")
       countries.add("Togo")
       countries.add("Nigeria")
       countries.add("South Africa")
       countries.add("South Sudan")
       UiHandler.post{
           Log.d(Main2Activity::class.java.simpleName, "$countries")
       }
   }
    private val callable = Callable {
        val result = mutableListOf<String>()
        result.add("issues")
        result.add("corruption")
        result.add("change")
        result.add("technology")
        result.add("revolution")
        return@Callable result
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        executor.execute(runnable)
        val future : Future<MutableList<String>> = executor.submit(callable)
        val data = future.get()
        Log.d(Main2Activity::class.java.simpleName, "$data")

    }



    override fun onDestroy() {
        super.onDestroy()
        executor.shutdownNow()
        executor.shutdown()
        executor.isShutdown
        UiHandler.removeCallbacks(runnable)
        UiHandler.removeCallbacksAndMessages(null)

    }
}
