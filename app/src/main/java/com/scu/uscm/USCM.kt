package com.scu.uscm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class USCM: Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}