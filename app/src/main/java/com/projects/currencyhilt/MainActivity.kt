package com.projects.currencyhilt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.hilt.work.HiltWorker
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.material.navigation.NavigationView
import com.projects.currencyhilt.databinding.ActivityMainBinding
import com.projects.currencyhilt.networkhelper.NetworkHelper
import com.projects.currencyhilt.viewmodels.CurrencyViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CurrencyViewModel by viewModels()
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main, R.id.nav_currency, R.id.nav_calculator
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.appBarMain.contentMain.bottomNav.setupWithNavController(navController)
        binding.appBarMain.contentMain.bottomNav.itemIconTintList = null
        binding.appBarMain.appbarlayout.outlineProvider = null
        navView.setNavigationItemSelectedListener(this)

//        val workRequest = PeriodicWorkRequest
//            .Builder(CurrencyWorker::class.java, 15, TimeUnit.MINUTES).build()
//
//        WorkManager.getInstance(this)
//            .enqueue(workRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        binding.appBarMain.toolbar.setNavigationIcon(R.drawable.indicator_drawer)
    }

    fun provideIndicator() {
        binding.appBarMain.toolbar.setNavigationIcon(R.drawable.indicator_drawer)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_about -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
            }
        }

        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

//    companion object {
//        private const val TAG = "MainActivity"
//
//        @HiltWorker
//        class CurrencyWorker @AssistedInject constructor(
//            @Assisted private val context: Context,
//            @Assisted workerParameters: WorkerParameters
//        ) :
//            Worker(context, workerParameters), CoroutineScope {
//
//            override fun doWork(): Result {
//                val networkHelper = NetworkHelper(context)
//                Toast.makeText(context, "Worker is now working", Toast.LENGTH_SHORT)
//                    .show()
//                Log.d(TAG, "Worker is now working")
//                lifecycleScope.launch {
//                    if (networkHelper.isNetworkConnected()) {
//                        viewModel.justAddCurrencies()
//                    }
//                }
//                return Result.success()
//            }
//
//            override val coroutineContext: CoroutineContext
//                get() = Job() + Dispatchers.Main
//        }
//    }


}