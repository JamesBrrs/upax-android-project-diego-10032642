package com.upax.androidproject.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class ApplicationApp: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: ApplicationApp
            private set
    }

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

    fun getContext(): Context {
        return instance.context
    }
}