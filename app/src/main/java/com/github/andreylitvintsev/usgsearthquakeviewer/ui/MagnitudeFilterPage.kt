package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.LazyPage


class MagnitudeFilterPage(val viewPager: ViewPager, context: Context) : LazyPage(context) {

    override fun getPlaceholderLayout(): Int = R.layout.stub

    override fun getLayout(): Int = R.layout.page_dialog_filter

    override fun onViewInflated(view: View) {
        view.findViewById<Button>(R.id.timePickerButton).setOnClickListener {
            viewPager.currentItem = 1 // TODO: исправить magic number
        }
    }

}
