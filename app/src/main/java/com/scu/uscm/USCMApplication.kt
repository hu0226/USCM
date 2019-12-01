package com.scu.uscm

import android.app.Application
import android.content.Intent
import android.util.Log
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.Region
import org.altbeacon.beacon.powersave.BackgroundPowerSaver
import org.altbeacon.beacon.startup.BootstrapNotifier
import org.altbeacon.beacon.startup.RegionBootstrap
import kotlin.properties.Delegates


class USCMApplication : Application(), BootstrapNotifier {

    private var regionBootstrap: RegionBootstrap? = null

    companion object {
        var appContext: USCMApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        Log.d("TAG", "App started up")
        val beaconManager = BeaconManager.getInstanceForApplication(this)
        val region = Region("com.scu.uscm.boostrapRegion", null, null, null)
        regionBootstrap = RegionBootstrap(this, region)
    }

    override fun didDetermineStateForRegion(p0: Int, p1: Region?) {
        // Don't care
    }

    override fun didEnterRegion(p0: Region?) {
        Log.d("TAG", "Got a didEnterRegion call")
        regionBootstrap?.disable()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        this.startActivity(intent)
    }

    override fun didExitRegion(p0: Region?) {
        // Don't care
    }
}