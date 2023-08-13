package com.example.jobscheduelers

import android.app.job.*
import android.widget.Toast

class DemoJob : JobService() {
    override fun onStartJob(parms: JobParameters?): Boolean {
        Toast.makeText(this,"DOING JOB",Toast.LENGTH_LONG).show()
        jobFinished(parms,false)

        //if want to do async job then return true
        //task going on
        //return true
        return false
    }

    override fun onStopJob(parms: JobParameters?): Boolean {
        //reschedule job -> true , don't -> false
        return false
    }

}