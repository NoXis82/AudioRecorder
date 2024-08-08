package com.noxis.audiorecorder.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import com.noxis.audiorecorder.service.MyService


object MyAppWidget : GlanceAppWidget() {

    val stateKey = booleanPreferencesKey("state")

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val state = currentState(key = stateKey) ?: false
            Log.d("TAG", "provideGlance: $state")
            Column(
                modifier = GlanceModifier.fillMaxSize().background(Color.DarkGray),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val str = if (state) "Stop" else "Rec"
                Button(text = str, onClick = actionRunCallback(MyActionCallback::class.java))
            }
        }
    }


}