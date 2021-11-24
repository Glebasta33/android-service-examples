package ru.sumin.servicestest

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PersistableBundle
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartCommand")
        val page = params?.extras?.getInt(PAGE) ?: 0
        coroutineScope.launch {
            for (i in 0 until 5) {
                delay(1000)
                log("Timer $i in page $page")
            }


            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }


    private fun log(message: String) {
        println("MyJobService $message")
    }

    companion object {
        const val JOB_ID = 111
        private const val PAGE = "page"
        fun newBundle(page: Int): PersistableBundle {
            return PersistableBundle().apply {
                putInt(PAGE, page)
            }
        }
    }

}