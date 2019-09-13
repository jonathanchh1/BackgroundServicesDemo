package com.emi.basicworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.emi.basicworkmanager.WorkerActivity.Companion.DataOutput
import com.emi.basicworkmanager.WorkerActivity.Companion.pushedData
import java.lang.Exception

class CustomWorker(context: Context,  params : WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
       return try {
           val job: Int? = inputData.getInt(pushedData, 0)
           var result  = 0
           if(job != null){
               result = job.times(2)
           }
           val outputData = workDataOf(Pair(DataOutput, result))
           return Result.success(outputData)
       }catch (e : Exception){
           Result.failure()
           Result.retry()
       }
    }

}
