package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.appCompatActivity
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.LazyPagerAdapter


// TODO: может ли существовать фрагмент без активити?

class FilterDialogFragment : AppCompatDialogFragment() { // TODO: посмотреть в чем разница между DialogFragment

    private lateinit var viewPager: ViewPager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(appCompatActivity())
            .setView(appCompatActivity().layoutInflater.inflate(R.layout.dialog_fragment_filter, null).apply {
                viewPager = findViewById(R.id.viewPager)
                viewPager.adapter = createPagerAdapter()
            })
            .create()
    }

    private fun createPagerAdapter() = LazyPagerAdapter(
        MagnitudeFilterPage(viewPager, appCompatActivity()),
        DateFilterPage(viewPager, appCompatActivity())
    )

}
