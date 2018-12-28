package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup


class LazyPagerAdapter(vararg val pages: LazyPage) : PagerAdapter() {

    private var currentPage: Int = -1

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)

        if (currentPage != position) {
            if (currentPage != -1) {
                pages[currentPage].onLeavedPage()
            }

            currentPage = position
            pages[currentPage].onPageStayVisible(currentPage)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewOnPosition = pages[position].onInstantiateItem(container, position)
        container.addView(viewOnPosition)
        return viewOnPosition
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun isViewFromObject(view: View, any: Any) = (view == any)

    override fun getCount(): Int = pages.size

}
