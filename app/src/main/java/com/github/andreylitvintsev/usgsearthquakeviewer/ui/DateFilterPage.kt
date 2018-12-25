package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.LazyPage


class DateFilterPage(val viewPager: ViewPager, context: Context) : LazyPage(context) {

//    override fun getPlaceholderLayout(): Int = R.layout.stub

    override fun getLayout(): Int = R.layout.page_dialog_datepicker

    override fun onPageStayVisible(pageIndex: Int) {
        super.onPageStayVisible(pageIndex)
        showMainView()
    }

    override fun onLeavedPage() {
        super.onLeavedPage()
        showPlaceHolder()
    }

    override fun onViewInflated(view: View) {
        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            viewPager.currentItem = 0 // TODO: Начни с меня!!! исправить magic number + в качестве возможного сценария создавать calendar view сразу
        }
    }
}