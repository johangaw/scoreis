package com.example.scoreis.utils

import android.util.Log

interface Logger {
    fun log(msg: String) {
        Log.d(this.javaClass.name, msg)
    }
}