package com.themoviedb.core.utils.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.snackbar.Snackbar

@Suppress("unused")
fun View.showSnackBar(msg: String?) {
    snackBarGenerator(msg).show()
}

@Suppress("unused")
fun View.showSnackBar(
    msg: String?,
    buttonActionText: String,
    buttonActionCallback: () -> Unit,
) {
    snackBarGenerator(msg)
        .setAction(buttonActionText) { buttonActionCallback() }
        .show()
}

@Suppress("unused")
fun View.showSnackBarClose(
    msg: String?,
    buttonActionText: String = "Dismiss",
    buttonActionTextColor: Int? = null,
) {
    snackBarGenerator(msg).apply {
        setAction(buttonActionText) { dismiss() }

        if(buttonActionTextColor != null){
            setActionTextColor(ContextCompat.getColor(context, buttonActionTextColor))
        }

        show()
    }
}

fun View.snackBarGenerator(msg: String?) = Snackbar
    .make(this, msg ?: "", Snackbar.LENGTH_SHORT)
    .setTextColor(Color.WHITE)

fun generateCircularProgress(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}