package com.emi.threadmanagementdemo

import android.os.AsyncTask
import android.util.Log

class CustomAsync : AsyncTask<Int, Int, Int>(){


    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Int?): Int {
        var total : Int = 0
        for((counter, number) in params.withIndex()){

            val result = "$number"
            total = total.plus(number!!)
            publishProgress(counter)
        }
        return total
    }
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d(CustomAsync::class.java.simpleName, "$values corrupt")
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        Log.d(CustomAsync::class.java.simpleName, "$result data sets")
    }

    override fun onCancelled() {
        super.onCancelled()
    }
}