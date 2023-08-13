package com.example.jobscheduelers

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var JOB_ID = 123
    fun scheduleJob() {
        val jobScheduler : JobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        //component == intent
        val jobInfo : JobInfo = JobInfo.Builder(JOB_ID, ComponentName(this,DemoJob::class.java))
            .setPeriodic(120000)
            .setPersisted(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setRequiresCharging(true)
                as JobInfo
        jobScheduler.schedule(jobInfo)
    }
}