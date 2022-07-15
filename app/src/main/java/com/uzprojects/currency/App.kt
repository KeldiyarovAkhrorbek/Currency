package com.uzprojects.currency

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application()


//    , Configuration.Provider {
//    @Inject
//    lateinit var workerFactory: HiltWorkerFactory
//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
//    }
//
//}