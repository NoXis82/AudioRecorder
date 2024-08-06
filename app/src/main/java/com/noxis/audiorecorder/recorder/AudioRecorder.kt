package com.noxis.audiorecorder.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}