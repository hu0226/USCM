package com.scu.uscm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import kotlin.properties.Delegates

class USCMApplication : Application() {

    companion object {
        var appContext: USCMApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}