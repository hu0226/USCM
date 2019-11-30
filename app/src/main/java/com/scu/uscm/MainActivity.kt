package com.scu.uscm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scu.uscm.board.BoardFragment
import com.scu.uscm.database.local.UscmDao
import com.scu.uscm.database.local.UscmDatabase
import com.scu.uscm.history.HistoryFragment
import com.scu.uscm.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private var db: UscmDatabase? = null
    private var uscmDao: UscmDao? = null

    private val navController: NavController by lazy { Navigation.findNavController(this, R.id.nav_host_fragment) }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
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

        checkLogin()
        setUpView()
        setupNavController()
    }

    private fun checkLogin() {
        db = UscmDatabase.getInstance(this)
        uscmDao = db?.uscmDao()

        GlobalScope.launch {
            Log.d("TAG", "data " + db?.uscmDao()?.getStudent())
            if (db?.uscmDao()?.getStudent() == null) {
                navController.navigate(NavGraphDirections.actionGlobalLoginFragment())
            }
        }
    }

    private fun setUpView() {
        // toolbar
        setSupportActionBar(toolbar)

        // bottom navigation
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
                R.id.historyFragment -> supportActionBar?.title = resources.getString(R.string.title_history)
                R.id.profileFragment -> supportActionBar?.title = resources.getString(R.string.title_profile)
                else -> {}
            }
        }
    }

    private fun getCurrentFrag(): Fragment? =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.primaryNavigationFragment

}
