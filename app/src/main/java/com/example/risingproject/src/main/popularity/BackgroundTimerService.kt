package com.example.risingproject.src.main.popularity

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.util.*
import kotlin.concurrent.timerTask

class BackgroundTimerService : Service() {
    private var timer: Timer? = null
    private var timertask: TimerTask? = null
    private var handler = Looper.getMainLooper()
    private var currTime = 0
    override fun onCreate() {
        super.onCreate()
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent == null) {
            Log.d("Service Intent", "진입 실패")
            return Service.START_STICKY
        } else {
            Log.d("Service Intent", "진입 완료 ${intent.getIntExtra("currTime_F",28800)}")
            var intentF = Intent(application,PopularityFragment::class.java)
            intentF.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            currTime = intent.getIntExtra("currTime_F",28800)
            //intent가 null이 아닌 경우에는 intent로 온 데이터를 뽑는다.
            Thread{
                Thread.sleep(1000)
                currTime--
                intentF.putExtra("currTime_S",currTime)
                Log.d("currTime", currTime.toString())
            }.start()
            startService(intentF)
        }
        return super.onStartCommand(intent, flags, startId)
    }
}