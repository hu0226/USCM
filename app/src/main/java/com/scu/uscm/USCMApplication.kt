package com.scu.uscm

import android.app.Application
import kotlin.properties.Delegates

class USCMApplication : Application() {

    companion object {
        var appContext: USCMApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}