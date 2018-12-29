package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.PendingPage


class MagnitudeFilterPage(val viewPager: ViewPager, context: Context) : PendingPage(context) {

    override fun getLayout(): Int = R.layout.page_dialog_filter

    override fun onPageStayVisible(pageIndex: Int) = showMainView()

    override fun onViewInflated(view: View) {
        super.onViewInflated(view)

        view.findViewById<Button>(R.id.timePickerButton).setOnClickListener {
            viewPager.currentItem = 1 // TODO: исправить magic number
        }
    }

}
