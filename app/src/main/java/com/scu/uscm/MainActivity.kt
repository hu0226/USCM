package com.scu.uscm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scu.uscm.board.BoardFragment
import com.scu.uscm.history.HistoryFragment
import com.scu.uscm.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy { Navigation.findNavController(this, R.id.nav_host_fragment) }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_dashboard -> {
                if (getCurrentNavFragment() is BoardFragment) {
                    return@OnNavigationItemSelectedListener false
                } else {
                    navController.navigate(R.id.action_global_boardFragment)
                    supportActionBar?.title = resources.getString(R.string.title_board)
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.navigation_history -> {
                if (getCurrentNavFragment() is HistoryFragment) {
                    return@OnNavigationItemSelectedListener false
                } else {
                    navController.navigate(R.id.action_global_historyFragment)
                    supportActionBar?.title = resources.getString(R.string.title_history)
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.navigation_account -> {
                if (getCurrentNavFragment() is ProfileFragment) {
                    return@OnNavigationItemSelectedListener false
                } else {
                    navController.navigate(R.id.action_global_profileFragment)
                    supportActionBar?.title = resources.getString(R.string.title_profile)
                    return@OnNavigationItemSelectedListener true
                }
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()
    }

    private fun setUpView() {
        // toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.title_board)

        // bottom navigation
        bottom_navigation_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun getCurrentNavFragment(): Fragment? =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.primaryNavigationFragment

}
