package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.CommandDelayer
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.PendingPage
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.toFormattedString
import java.util.*


class MagnitudeFilterPage(
    val viewPager: ViewPager,
    context: Context,
    commandDelayer: CommandDelayer,
    val date: Date
) : PendingPage(context, commandDelayer) {

    private var timePickerButton: Button? = null

    override fun getPlaceholderLayout(): Int = R.layout.placeholder_dialog_filter

    override fun getLayout(): Int = R.layout.page_dialog_filter

    override fun onPageStayVisible(pageIndex: Int) {
        showMainView(delay = 300)
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
