package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.content.Context
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.PendingPage


class DateFilterPage(val viewPager: ViewPager, context: Context) : PendingPage(context) {

//    override fun getPlaceholderLayout(): Int = R.layout.stub

    override fun getLayout(): Int = R.layout.page_dialog_datepicker

    override fun onInstantiatedPage(pageIndex: Int) {
        showPlaceHolder()
    }

    override fun onPageStayVisible(pageIndex: Int) {
        super.onPageStayVisible(pageIndex)
        showMainView()
    }

    override fun onLeavedPage() {
        super.onLeavedPage()
        showPlaceHolder()
    }

    override fun onViewInflated(view: View) {
        super.onViewInflated(view)

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            viewPager.currentItem = 0 // TODO: Начни с меня!!! исправить magic number + в качестве возможного сценария создавать calendar view сразу
        }
    }
}
