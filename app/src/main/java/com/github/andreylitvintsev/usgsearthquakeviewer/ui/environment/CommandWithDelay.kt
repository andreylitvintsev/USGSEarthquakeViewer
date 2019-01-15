package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

/**
 * Data class contains command with delay in milliseconds
 */
data class CommandWithDelay(val command: Runnable, val delay: Long)
