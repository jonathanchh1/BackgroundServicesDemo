package com.emi.threadMessager

import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emi.threadmanagementdemo.R

class Main3Activity : AppCompatActivity() {

    private val runnable = Runnable {
        val result = mutableListOf<String>()
        result.add("test1")
        result.add("test2")
        result.add("test3")
        result.add("test4")
        requestHandler.sendMessage(Message())
        requestHandler.post{
            Log.d(Main3Activity::class.java.simpleName, "$result")
        }
    }

    private val requestHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(Main3Activity::class.java.simpleName, " $msg")
        }
    }

    val handlerThread = HandlerThread("its done processing")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        handlerThread.start()

      requestHandler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        requestHandler.removeCallbacksAndMessages(null)
        handlerThread.quitSafely()
        handlerThread.interrupt()
    }

}
