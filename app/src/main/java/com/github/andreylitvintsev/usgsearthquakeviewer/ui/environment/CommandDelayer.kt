package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment


interface CommandDelayer {

    fun launch(delay: Long, command: Runnable): Boolean

}
