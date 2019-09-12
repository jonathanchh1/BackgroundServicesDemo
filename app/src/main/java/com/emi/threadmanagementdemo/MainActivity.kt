package com.emi.threadmanagementdemo

import android.graphics.Region
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.ref.WeakReference
import java.util.*

class MainActivity : AppCompatActivity(), Listener {

   private var regionAsync: RegionalAsync? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (regionAsync?.status != AsyncTask.Status.RUNNING) {
            regionAsync = RegionalAsync(this)
        }
        val data = mutableListOf<Int>()
        data.add(1)
        data.add(2)
        data.add(3)
        data.add(4)
        data.add(5)
        data.add(8)
        data.add(9)
        data.add(50)
        data.add(20)
        regionAsync?.execute(data)

    }

    override fun onFetch(result: Int) {
        Log.d(MainActivity::class.java.simpleName, "$result all data")
    }

    override fun onProgress(progress: Int) {
        Log.d(MainActivity::class.java.simpleName, "$progress index value")
    }

    override fun onStarting(start: String) {
        Log.d(MainActivity::class.java.simpleName, "$start method")
    }

    override fun onCancel(cancel: String) {
        Log.d(MainActivity::class.java.simpleName, "$cancel method")
    }

    class RegionalAsync(fetchListener : Listener) : AsyncTask<MutableList<Int>, Int, MutableList<Int>>(){

        private var status = ""
        private var listener = WeakReference<Listener>(fetchListener)
        override fun onPreExecute() {
            super.onPreExecute()
            status = "its starting"
            listener.get()?.onStarting(status)

        }

        override fun doInBackground(vararg params: MutableList<Int>?): MutableList<Int> {
            val resultTotal  = mutableListOf<Int>()
            for((counter, digits) in params.withIndex()){
                val result = digits?.plus(3)
                result?.forEach {
                    resultTotal.add(it)
                    publishProgress(counter)
                }
            }
            return resultTotal
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values.forEach {
                listener.get()?.onProgress(it!!)
            }
        }

        override fun onPostExecute(result: MutableList<Int>?) {
            super.onPostExecute(result)
            for(items in result?.iterator()!!)
            listener.get()?.onFetch(items)
        }


        override fun onCancelled() {
            super.onCancelled()
            status = "cancelling..."
            listener.get()?.onCancel(status)

        }
    }


}

interface Listener{
    fun onStarting(start : String)
    fun onFetch(result : Int)
    fun onProgress(progress : Int)
    fun onCancel(cancel : String)
}

