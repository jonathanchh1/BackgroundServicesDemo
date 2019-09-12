package com.emi.senddatawithmesseger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.emi.threadmanagementdemo.R
import java.util.*

class Main4Activity : AppCompatActivity() {

    private val START = 1
    private val FINISH = 2

    val runnble = Runnable {
        val rn = Random()
        val number = rn.nextInt(300)
        requestHandler.sendMessage(createMessage(START, number))
        requestHandler.post {
            Log.d(Main4Activity::class.java.simpleName, "$number all number")
        }

    }

    val thread : Thread = Thread(runnble)
    val requestHandler = object : Handler(Looper.getMainLooper()){

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            msg.let {
                if(it.what == START && thread.state == Thread.State.NEW){
                    thread.start()
                }

                if(it.what == FINISH && thread.state == Thread.State.TERMINATED){
                    thread.interrupt()
                }
            }
        }
    }

    private fun createMessage(whats : Int, obj : Any) : Message{
        val msg = Message()
        msg.what = whats
        msg.obj = obj
        return msg
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        requestHandler.sendEmptyMessage(START)


    }

    override fun onResume() {
        super.onResume()
        thread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        requestHandler.removeCallbacksAndMessages(null)
        thread.interrupt()
        thread.isInterrupted
    }
}


