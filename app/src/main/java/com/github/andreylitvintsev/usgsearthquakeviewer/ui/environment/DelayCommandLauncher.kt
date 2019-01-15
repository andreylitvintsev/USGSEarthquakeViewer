package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.os.Handler
import androidx.lifecycle.Lifecycle


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

    /**
     * Invoke for launch command
     *
     * @param commandWithDelay data class contains command with delay in milliseconds
     * @see CommandWithDelay
     * @return result of "Handler.postDelayed()" method
     * @see Handler.postDelayed
     */
    override fun launch(commandWithDelay: CommandWithDelay) = launch(commandWithDelay.delay, commandWithDelay.command)
}
