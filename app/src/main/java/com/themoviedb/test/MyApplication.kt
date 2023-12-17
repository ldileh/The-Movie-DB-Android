package com.themoviedb.test

import android.app.Application
import com.themoviedb.test.config.GlobalConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        // configure timber
        if (GlobalConfig.IS_DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}