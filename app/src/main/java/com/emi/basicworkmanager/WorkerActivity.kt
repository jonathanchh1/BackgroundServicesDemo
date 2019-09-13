package com.emi.basicworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.emi.threadmanagementdemo.R
import kotlinx.android.synthetic.main.activity_worker.*
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class WorkerActivity : AppCompatActivity() {

    private lateinit var workerid : UUID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        worker_button.setOnClickListener {
            onStartRequest()
          observeWorkStatus().observe(this, Observer<WorkInfo> {

              Toast.makeText(
                  applicationContext,
                  "${it.outputData.getInt(DataOutput, 0)}",
                  Toast.LENGTH_SHORT
              ).show()
          })
        }
    }



    private fun onStartRequest(){
        val workerRequest = createWorkerRequest()
        WorkManager.getInstance(this).enqueue(workerRequest)
        workerid = workerRequest.id
    }

    private fun createWorkerRequest() : WorkRequest{
        val rn = Random().nextInt(30)
        val inputData = workDataOf(Pair(pushedData, rn))

        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

     return  OneTimeWorkRequestBuilder<CustomWorker>()
            .setInputData(inputData)
            .setConstraints(constraint)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
            .setInitialDelay(2, TimeUnit.SECONDS)
            .build()


    }

    private fun observeWorkStatus() : LiveData<WorkInfo> {
       return WorkManager.getInstance(this).getWorkInfoByIdLiveData(workerid)
    }

    companion object{
        const val pushedData = "calculation"
        const val DataOutput = "result"
    }
}
