package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.DelayCommandLauncher
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.PendingPagerAdapter
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.appCompatActivity
import java.util.*


// TODO: может ли существовать фрагмент без активити?

class FilterDialogFragment : AppCompatDialogFragment() { // TODO: посмотреть в чем разница между DialogFragment

    private companion object {
        const val SAVED_DATE_KEY = "savedDate"
    }

    private lateinit var viewPager: ViewPager
    private val date = Date() // TODO: сделать сохранение на поворотах

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        restoreState(savedInstanceState)

        DelayCommandLauncher(lifecycle).launch(5000) {
            Log.d("HANDLE","Hello World")
        }

        return AlertDialog.Builder(appCompatActivity())
            .setView(appCompatActivity().layoutInflater.inflate(R.layout.dialog_fragment_filter, null).apply {
                viewPager = findViewById(R.id.viewPager)
                viewPager.adapter = createPagerAdapter()
            })
            .create()
    }

    private fun createPagerAdapter() = PendingPagerAdapter(
        MagnitudeFilterPage(viewPager, appCompatActivity(), date),
        DateFilterPage(viewPager, appCompatActivity(), date)
    )

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState(outState)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            date.time = savedInstanceState.getLong(SAVED_DATE_KEY)
        }
    }

    private fun saveState(outState: Bundle) {
        outState.putLong(SAVED_DATE_KEY, date.time)
    }

}
