package com.example.movieapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.ui.shared.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)

        // Since HomeFragment doesn't have its own toolbar, the MainActivity's ActionBar
        // will be used. We don't need AppBarConfiguration for a single top-level destination.
        // setupActionBarWithNavController(navController)

        // Invalidate the options menu to ensure it's drawn
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_layout -> {
                sharedViewModel.toggleLayout()
                updateLayoutToggleIcon(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateLayoutToggleIcon(menuItem: MenuItem) {
        if (sharedViewModel.isGridLayout.value) {
            menuItem.setIcon(R.drawable.ic_list_layout)
        } else {
            menuItem.setIcon(R.drawable.ic_grid_layout)
        }
    }
}