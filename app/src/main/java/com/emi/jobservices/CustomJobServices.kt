package com.emi.jobservices

import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.app.job.JobWorkItem
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class CustomJobServices : JobService(){

    private val executor : ExecutorService = Executors.newSingleThreadExecutor()

    @TargetApi(Build.VERSION_CODES.O)
    override fun onStartJob(param: JobParameters?): Boolean {

        val integer = param?.transientExtras?.getInt("data")

        val callable = Callable {
            val result = integer?.times(3)
            return@Callable result
        }
         executor.submit(callable).get()
        jobFinished(param, false)

        return false
    }


    override fun onStopJob(params: JobParameters?): Boolean {
        jobFinished(params, false)
        return false
    }
}