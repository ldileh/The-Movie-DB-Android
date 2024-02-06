package com.themoviedb.test.util.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun AppCompatActivity.observeFlows(
    crossinline observationFunction: suspend (CoroutineScope) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            observationFunction(this)
        }
    }
}