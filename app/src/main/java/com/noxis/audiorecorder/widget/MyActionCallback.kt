package com.noxis.audiorecorder.widget

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import com.noxis.audiorecorder.service.MyService

class MyActionCallback : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        updateAppWidgetState(context, glanceId) { mutablePreferences ->
            val currentState = mutablePreferences[MyAppWidget.stateKey]
            if (currentState != null) {
                mutablePreferences[MyAppWidget.stateKey] = !currentState
            } else {
                mutablePreferences[MyAppWidget.stateKey] = true
            }

            val action = if (mutablePreferences[MyAppWidget.stateKey] == true) {
                MyService.Actions.START_RECORD.name

            } else {
                MyService.Actions.STOP_RECORD.name
            }
            Intent(context, MyService::class.java).also { intent ->
                intent.action = action
                context.startForegroundService(intent)
            }

            MyAppWidget.update(context, glanceId)
        }
    }


}