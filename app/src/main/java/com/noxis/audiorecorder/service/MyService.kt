package com.noxis.audiorecorder.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.noxis.audiorecorder.R
import com.noxis.audiorecorder.playback.AndroidAudioPlayer
import com.noxis.audiorecorder.recorder.AndroidAudioRecorder
import java.io.File

class MyService : Service() {
    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    private var audioFile: File? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "onStartCommand: ${intent?.action}")
        when (intent?.action) {
            Actions.START_RECORD.name -> {
                File(cacheDir, "audio.mp3").also {
                    recorder.start(it)
                    audioFile = it
                }
                start()
            }

            Actions.STOP_RECORD.name -> recorder.stop()


            Actions.START_PLAY.name -> {
                audioFile?.let {
                    player.playFile(it)
                }
            }

            Actions.STOP_PLAY.name -> player.stop()

            Actions.STOP_SERVICE.name -> stopSelf()
        }


        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun start() {
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Run is active")
            .setContentText("Elapsed time: 00:50")
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(1, notification)
        } else {
            startForeground(1, notification,
                FOREGROUND_SERVICE_TYPE_MICROPHONE)
        }

    }


    enum class Actions {
        START_RECORD,
        STOP_RECORD,
        START_PLAY,
        STOP_PLAY,
        STOP_SERVICE
    }

}