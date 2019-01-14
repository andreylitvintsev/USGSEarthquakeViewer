package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.os.Handler
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 * Lifecycle safety wrapper for "Handler.postDelayed"
 */
class DelayCommandLauncher(private val lifecycle: Lifecycle) {

    private val handler = Handler()
    private lateinit var command: Runnable

    private val commandBreaker = DelayCommandBreaker()

    init {
        lifecycle.addObserver(commandBreaker)
    }

    /**
     * Invoke for launch command
     * @param delay delay in millisecond
     * @param command the command which need launch
     */
    fun launch(delay: Long, command: Runnable) {
        this.command = command
        handler.postDelayed(this.command, delay)
    }

    private inner class DelayCommandBreaker : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun disableCommand() {
            Log.d("CHECK", "disableCommand")
            handler.removeCallbacks(command)
            lifecycle.removeObserver(commandBreaker)
        }

    }

}
