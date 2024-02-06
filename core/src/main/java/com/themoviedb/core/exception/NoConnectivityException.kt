package com.themoviedb.core.exception

import java.io.IOException


class NoConnectivityException : IOException() {
    override val message: String
        get() = "Sorry we can't connect"
    // You can send any message whatever you want from here.
}