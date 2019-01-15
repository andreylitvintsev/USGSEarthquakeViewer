package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


internal class SelfDestructCommand(
    private val lifecycle: Lifecycle,
    val command: Runnable,
    val delay: Long
) : Runnable, LifecycleObserver {

    private companion object {
        val handler = Handler()
    }

    init {
        lifecycle.addObserver(this)
    }

    fun startCommand(): Boolean {
        return if (delay > 0) {
            handler.postDelayed(this, delay)
        } else {
            run()
            true
        }
    }

    override fun run() = command.run()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destructCommand() {
        handler.removeCallbacks(this)
        lifecycle.removeObserver(this)
    }

}
