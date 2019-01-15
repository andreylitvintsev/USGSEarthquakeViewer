package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 * Lifecycle safety wrapper for "Handler.postDelayed()"
 *
 * @see Handler.postDelayed
 */
class DelayCommandLauncher(private val lifecycle: Lifecycle) : CommandDelayer {

    /**
     * Invoke for launch command
     *
     * @param delay delay in millisecond
     * @param command the command which need launch
     * @return result of "Handler.postDelayed()" method
     * @see Handler.postDelayed
     */
    override fun launch(delay: Long, command: Runnable) = SelfDestructCommand(lifecycle, command, delay).startCommand()

}

private class SelfDestructCommand(
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

    fun startCommand() = handler.postDelayed(this, delay)

    override fun run() = command.run()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destructCommand() {
        handler.removeCallbacks(this)
        lifecycle.removeObserver(this)
    }

}
