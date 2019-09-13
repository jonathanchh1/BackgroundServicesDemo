package com.emi.advancedworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class PartWorker(context: Context, params : WorkerParameters) : Worker(context, params){


    override fun doWork(): Result {

        val color = inputData.getString("color")
        val size = inputData.getString("size")

        var newColor  = workDataOf()

        if(color != null) {
             newColor = workDataOf(Pair("RESULT", "green $color"))
           return  Result.success(newColor)
         }else{
             Result.failure(newColor)
         }

        var newSize = workDataOf()
        if(size != null){
            newSize = workDataOf(Pair("RESULT", "large $size"))
            return Result.success(newSize)
        }else{
          return Result.failure(newSize)
        }

    }


}