package com.noxis.audiorecorder

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.noxis.audiorecorder.ui.theme.AudioRecorderTheme
import com.noxis.audiorecorder.service.MyService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.FOREGROUND_SERVICE_MICROPHONE
            ),
            0
        )
        enableEdgeToEdge()
        setContent {
            var stateRecord by remember { mutableStateOf(true) }
            var statePlay by remember { mutableStateOf(true) }
            AudioRecorderTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        enabled = stateRecord,
                        onClick = {
                            stateRecord = !stateRecord
                            Intent(applicationContext, MyService::class.java).also { intent ->
                                intent.action = MyService.Actions.START_RECORD.name
                                startService(intent)
                            }

                        }) {
                        Text(text = "Start recording")
                    }
                    Button(
                        enabled = !stateRecord,
                        onClick = {
                            stateRecord = !stateRecord
                            Intent(applicationContext, MyService::class.java).also { intent ->
                                intent.action = MyService.Actions.STOP_RECORD.name
                                startService(intent)
                            }

                        }) {
                        Text(text = "Stop recording")
                    }
                    Button(
                        enabled = statePlay,
                        onClick = {
                            statePlay = !statePlay
                            Intent(applicationContext, MyService::class.java).also { intent ->
                                intent.action = MyService.Actions.START_PLAY.name
                                startService(intent)
                            }

                        }) {
                        Text(text = "Play")
                    }
                    Button(
                        enabled = !statePlay,
                        onClick = {
                            statePlay = !statePlay
                            Intent(applicationContext, MyService::class.java).also { intent ->
                                intent.action = MyService.Actions.STOP_PLAY.name
                                startService(intent)
                            }
                        }) {
                        Text(text = "Stop playing")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Intent(applicationContext, MyService::class.java).also { intent ->
            intent.action = MyService.Actions.STOP_SERVICE.name
            startService(intent)
        }
    }
}
