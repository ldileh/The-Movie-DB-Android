package com.themoviedb.core.utils.ext

import android.app.Activity
import android.content.Context
import android.widget.Toast

@Suppress("unused")
fun Activity.forceCloseApp(){
    showToast("Close app")

    try {
        finishAffinity()
    }catch (e: Exception){
        logError(msg = e.message, e = e)
    }
}

@Suppress("unused")
fun Context.forceCloseApp(){
    if (this is Activity){
        forceCloseApp()
    }
}

@Suppress("unused")
fun Context.showToast(msg: String?) = try {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}catch (e: Exception){
    logError(msg = e.message, e = e)
}
