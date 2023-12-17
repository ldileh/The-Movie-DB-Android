package com.themoviedb.test.config

import com.themoviedb.test.BuildConfig

object GlobalConfig {
    // check if build app is debug or not
    const val IS_DEBUG = BuildConfig.BUILD_TYPE == "debug"

    // name of app
    private const val APP_NAME = "app"
}