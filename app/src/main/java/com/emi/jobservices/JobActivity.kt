package com.emi.jobservices

import android.annotation.TargetApi
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.emi.threadmanagementdemo.R
import kotlinx.android.synthetic.main.activity_job.*
import java.util.*

class JobActivity : AppCompatActivity() {

    lateinit var jobDispatcher : JobScheduler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)
        jobDispatcher = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        job_button.setOnClickListener {
            val resultCode = jobDispatcher.schedule(createJobInfo())
            if (resultCode == JobScheduler.RESULT_SUCCESS){
                Toast.makeText(applicationContext, "$resultCode VALUE", Toast.LENGTH_SHORT).show()
            }else{
                Log.d(JobActivity::class.java.simpleName, "$resultCode error")
            }
        }


    }


    private val bundle = Bundle().apply {
        val result = Random().nextInt(30)
        putInt("data", result)
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun createJobInfo() : JobInfo{
        val componentName = ComponentName(this, CustomJobServices::class.java)
        return JobInfo.Builder(1, componentName)
            .setMinimumLatency(1000)
            .setTransientExtras(bundle)
            .setOverrideDeadline(3000)
            .setPrefetch(true)
            .build()
    }

}
