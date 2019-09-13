package com.emi.advancedworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.lang.Exception
import java.util.*

class FinalWorker(context: Context, params : WorkerParameters) : Worker(context, params){


    override fun doWork(): Result {

        val input = inputData.getStringArray("RESULT")
        Log.d(FinalWorker::class.java.simpleName, "$input values")
         if(input != null){
                 val color = input.first()
                  val size = input.last()
                 val result = color.plus(size)
                 val output = workDataOf(Pair("FINISHED", result))
                 Log.d(FinalWorker::class.java.simpleName,"${output.getString("FINISHED")}")
                 return Result.success(output)
         }
        return Result.success()
    }
}