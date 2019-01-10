package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.toFormattedString
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.PendingPage
import java.util.*


class MagnitudeFilterPage(val viewPager: ViewPager, context: Context, val date: Date) : PendingPage(context) {

    private var timePickerButton: Button? = null

    override fun getLayout(): Int = R.layout.page_dialog_filter

    override fun onPageStayVisible(pageIndex: Int) {
        showMainView()
        timePickerButton?.text = date.toFormattedString()
    }

    override fun onViewInflated(rootView: View) {
        super.onViewInflated(rootView)

        initViews(rootView)
    }

    private fun initViews(rootView: View) {
        timePickerButton = initTimePickerButton(rootView)
    }

    private fun initTimePickerButton(rootView: View): Button {
        return rootView.findViewById<Button>(R.id.timePickerButton).apply {
            text = date.toFormattedString()

            setOnClickListener {
                toNextPage()
            }
        }
    }

    private fun toNextPage() {
        viewPager.currentItem += 1
    }

}
