package com.sample

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sample.omdb.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnNavigatedListener {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchTab(R.navigation.omdb, R.id.movieSearch)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchTab(R.navigation.kitsu, R.id.animeSearch)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchTab(R.navigation.reddit, R.id.redditSearch)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun switchTab(@NavigationRes graphId: Int, @IdRes destinationId: Int) {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.graph.id != graphId) {
            while (navController.popBackStack()) {}
            navController.setGraph(graphId)
            navController.navigate(destinationId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onStart() {
        super.onStart()

        findNavController(R.id.nav_host_fragment).addOnNavigatedListener(this)
    }

    override fun onStop() {
        super.onStop()

        findNavController(R.id.nav_host_fragment).removeOnNavigatedListener(this)
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        val isStartDestination = controller.currentDestination?.id == controller.graph.startDestination
        supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
