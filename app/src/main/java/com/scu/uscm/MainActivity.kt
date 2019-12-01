package com.scu.uscm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scu.uscm.board.BoardFragment
import com.scu.uscm.database.local.UscmDao
import com.scu.uscm.database.local.UscmDatabase
import com.scu.uscm.database.remote.Firebase
import com.scu.uscm.history.HistoryFragment
import com.scu.uscm.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.altbeacon.beacon.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity(), BeaconConsumer {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
        const val BEACON_RANGING_ID = "myRangingUniqueId"
        const val BEACON_MONITORING_ID = "myMonitorUniqueId"
        const val ALTBEACON_FORMAT = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"
        const val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

        private const val CHANNEL_ID = "com.scu.uscm"
        private const val CHANNEL_NAME = "MyChannel"
        private const val notificationId = 8
    }

    private val db: UscmDatabase by lazy { UscmDatabase.getInstance(this) }
    private lateinit var uscmDao: UscmDao
    private var hasSignedAndNotified = false

    private var beaconManager: BeaconManager? = null

    private val formatter: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.TAIWAN)
    }

    private val hourFormatter: SimpleDateFormat by lazy {
        SimpleDateFormat("HH", Locale.TAIWAN)
    }

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_dashboard -> {
                    if (getCurrentFrag() is BoardFragment) {
                        return@OnNavigationItemSelectedListener false
                    } else {
                        navController.navigate(R.id.action_global_boardFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_history -> {
                    if (getCurrentFrag() is HistoryFragment) {
                        return@OnNavigationItemSelectedListener false
                    } else {
                        navController.navigate(R.id.action_global_historyFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_account -> {
                    if (getCurrentFrag() is ProfileFragment) {
                        return@OnNavigationItemSelectedListener false
                    } else {
                        navController.navigate(R.id.action_global_profileFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uscmDao = db.uscmDao()

        checkLogin()
        setUpView()
        setupNavController()
        checkPermission()
        setupBeacon()
    }

    private fun getCurrentFrag(): Fragment? =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.primaryNavigationFragment

    private fun checkLogin() {
        GlobalScope.launch {
            Log.d("TAG", "data " + db.uscmDao().getStudent())
            if (db.uscmDao().getStudent() === null) {
                navController.navigate(NavGraphDirections.actionGlobalLoginFragment())
            }
        }
    }

    private fun setUpView() {
        setSupportActionBar(toolbar)
        bottom_navigation_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupNavController() {
        navController.addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            when (navController.currentDestination?.id) {
                R.id.loginFragment -> {
                    supportActionBar?.title = resources.getString(R.string.title_login)
                    bottom_navigation_view.visibility = View.GONE
                }
                R.id.boardFragment -> {
                    supportActionBar?.title = resources.getString(R.string.title_board)
                    bottom_navigation_view.visibility = View.VISIBLE
                }
                R.id.historyFragment ->
                    supportActionBar?.title = resources.getString(R.string.title_history)
                R.id.profileFragment ->
                    supportActionBar?.title = resources.getString(R.string.title_profile)
                else -> {}
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val location = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            val bluetooth = checkSelfPermission(Manifest.permission.BLUETOOTH)
            val bluetoothAdmin = checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN)

            if (
                location != PackageManager.PERMISSION_GRANTED ||
                bluetooth != PackageManager.PERMISSION_GRANTED ||
                bluetoothAdmin != PackageManager.PERMISSION_GRANTED) {

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("This app needs permission access")
                builder.setMessage("Please grant permission so this app can detect beacons.")
                builder.setPositiveButton(android.R.string.ok) { _,_ ->
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN),
                        PERMISSION_REQUEST_CODE
                    )
                }

                builder.setOnDismissListener {

                }
                builder.show()
            }
        }
    }

    private fun setupBeacon() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
        beaconManager?.bind(this)

//        beaconManager?.beaconParsers?.clear()
//        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
//        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout("m:0-1=5900,i:2-2,i:3-4,p:5-5"))
//        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT))
//        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT))
//        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT))
//        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT))
//        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(BeaconParser.URI_BEACON_LAYOUT))
    }

    override fun onBeaconServiceConnect() {

        // Region(uniqueId, Identifier id1, Identifier id2, Identifier id3)
        // 利用傳入参數可以選擇性的獲取指定 Beacon 信號。若没有傳入相關参數，表示獲取所有 Beacon 信號
        val rangeNotifier = RangeNotifier { beacons, region ->
            Log.d("TAG", "didRangeBeaconsInRegion called with beacon count:  ${beacons.size}")

            if (beacons.size > 0 && !hasSignedAndNotified) {
                GlobalScope.launch {
                    if (db.uscmDao().getStudent() !== null) {
                        Firebase.instance.signClass(
                            db.uscmDao().getStudent().id,
                            hourFormatter.format(Date()),
                            formatter.format(Date()),
                            this@MainActivity.applicationContext,
                            navController
                        )
                        hasSignedAndNotified = true
                    }
                }

//                sendNotification()
            }
        }

        try {
            beaconManager?.startRangingBeaconsInRegion(Region(BEACON_RANGING_ID, null, null, null))
            beaconManager?.addRangeNotifier(rangeNotifier)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

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

    private fun sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "description"
            val notificationManager: NotificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_child_care_black_24dp)
                .setContentTitle(resources.getString(R.string.notification_title))
                .setContentText(resources.getString(R.string.notification_content))
                .setVibrate(longArrayOf(500, 500, 1000, 2000, 3000, 500, 500, 500))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent())
                .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId, builder.build())
    }

    private fun getPendingIntent(): PendingIntent? {
        val bundle = Bundle()
        bundle.putString("params", resources.getString(R.string.notification_params))
        return Navigation
            .findNavController(this, R.id.nav_host_fragment)
            .createDeepLink()
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.boardFragment)
            .setArguments(bundle)
            .createPendingIntent()
    }

    override fun onDestroy() {
        super.onDestroy()
        beaconManager?.unbind(this)
    }
}
