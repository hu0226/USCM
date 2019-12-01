package com.scu.uscm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.altbeacon.beacon.*


open class BaseActivity : AppCompatActivity(), BeaconConsumer {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
        const val IBEACON_FORMAT = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"
        const val BEACON_MONITORING_ID = "myMonitoringUniqueId"
        const val BEACON_RANGING_ID = "myRangingUniqueId"
    }

    private var beaconManager: BeaconManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.statusBarColor = ContextCompat.getColor(this, R.color.gray_888888)

        setUpBeaconManager()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val location = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            val bluetooth = checkSelfPermission(Manifest.permission.BLUETOOTH)
            val bluetoothAdmin = checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN)

            if (location != PackageManager.PERMISSION_GRANTED ||
                bluetooth != PackageManager.PERMISSION_GRANTED ||
                bluetoothAdmin != PackageManager.PERMISSION_GRANTED) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("This app needs permission access")
                builder.setMessage("Please grant permission so this app can detect beacons.")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setOnDismissListener {
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN),
                        PERMISSION_REQUEST_CODE
                    )
                }
                builder.show()
            }
        }
    }

    private fun setUpBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
        beaconManager?.bind(this)
    }

    override fun onBeaconServiceConnect() {

        // Region(uniqueId, Identifier id1, Identifier id2, Identifier id3)
        // 利用傳入参數可以選擇性的獲取指定 Beacon 信號。若没有傳入相關参數，表示獲取所有 Beacon 信號

        beaconManager?.addRangeNotifier(object : RangeNotifier {
            // 不斷接收到 Beacon 信號
            override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
                if (beacons?.size!! > 0) {
                    // Perform distance-specific action here
                    beacons.iterator().next().apply {
                        Log.i("TAG", "The first beacon I see is about ${this.distance} meters away.")
                        Log.i("TAG", "bluetoothName ${this.bluetoothName}")
                        Log.i("TAG", "beaconTypeCode ${this.beaconTypeCode}")
                        Log.i("TAG", "bluetoothAddress ${this.bluetoothAddress}")
                        Log.i("TAG", "serviceUuid ${this.serviceUuid}")
                        Log.i("TAG", "rssi ${this.rssi}") // RSSI 來進行距離判斷

                        // iBeacon 主要參數為以下四個組成
                        Log.i("TAG", "txPower ${this.id1}")     // UUID
                        Log.i("TAG", "txPower ${this.id2}")     // Major
                        Log.i("TAG", "txPower ${this.id3}")     // Minor
                        Log.i("TAG", "txPower ${this.txPower}") // txPower
                    }
                }
            }
        })

        try {
            beaconManager?.startRangingBeaconsInRegion(Region(BEACON_RANGING_ID, null, null, null))
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        try {
            beaconManager?.stopRangingBeaconsInRegion(Region(BEACON_RANGING_ID, null, null, null))
        } catch (e: RemoteException) {
            e.printStackTrace()
        }


        // monitor
        beaconManager?.addMonitorNotifier( object: MonitorNotifier {
            override fun didDetermineStateForRegion(state: Int, region: Region?) {
                Log.i("TAG", "I just saw an beacon for the first time! $state")
                Log.i("TAG", "uniqueId ${region?.uniqueId}")
            }

            override fun didEnterRegion(region: Region?) {
                Log.i("TAG", "I no longer see an beacon")
            }

            override fun didExitRegion(region: Region?) {
                Log.i("TAG", "I have just switched from seeing/not seeing beacons: ")
            }
        } )

        try {
            beaconManager?.startMonitoringBeaconsInRegion(Region(BEACON_MONITORING_ID, null, null, null))
        } catch (e: RemoteException) {
            Log.i("TAG", "error$e")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beaconManager?.unbind(this)
    }
}