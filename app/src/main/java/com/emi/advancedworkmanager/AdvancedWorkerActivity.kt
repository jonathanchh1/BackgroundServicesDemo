package com.emi.advancedworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.emi.threadmanagementdemo.R
import kotlinx.android.synthetic.main.activity_advanced_worker.*
import java.util.*
import java.util.concurrent.TimeUnit

class AdvancedWorkerActivity : AppCompatActivity() {

    private lateinit var workerId : UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_worker)

        advanced_worker.setOnClickListener {
            onStartWorker()
            onObserverStatus().observe(this, Observer {
              Toast.makeText(applicationContext, "${it.outputData.getString("FINISHED")}", Toast.LENGTH_SHORT).show()
            })
        }

    }


    private fun onStartWorker(){
        val finalRequest = createFinalRequest()
        WorkManager.getInstance(this)
            .beginWith(Arrays.asList(createFirstRequest(), createSecRequest()))
            .then(finalRequest)
            .enqueue()

        workerId = finalRequest.id

    }

    private fun createFirstRequest() : OneTimeWorkRequest{
        val input = workDataOf(Pair("color", "red"))
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        return OneTimeWorkRequestBuilder<PartWorker>()
            .setConstraints(constraint)
            .setInputData(input)
            .setInitialDelay(3, TimeUnit.SECONDS)
            .build()
    }

    private fun createSecRequest() : OneTimeWorkRequest{

        val input = workDataOf(Pair("size", "small"))
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        val request = OneTimeWorkRequestBuilder<PartWorker>()
            .setInputData(input)
            .setConstraints(constraints)
            .build()
        return request

    }

    private fun createFinalRequest() : OneTimeWorkRequest{
        return OneTimeWorkRequestBuilder<FinalWorker>()
            .setInputMerger(ArrayCreatingInputMerger::class.java)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()
    }

    private fun onObserverStatus() : LiveData<WorkInfo> {
        return WorkManager.getInstance(this).getWorkInfoByIdLiveData(workerId)
    }
}
