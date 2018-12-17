package com.github.andreylitvintsev.usgsearthquakeviewer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: может ли существовать фрагмент без активити?
class FilterDialogFragment : AppCompatDialogFragment() { // TODO: посмотреть в чем разница между DialogFragment

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(appCompatActivity())
            .setView(appCompatActivity().layoutInflater.inflate(R.layout.dialog_fragment_filter, null).apply {
                val viewPager = this.findViewById<ViewPager>(R.id.viewPager)
                viewPager.adapter = MyPagerAdapter(appCompatActivity().layoutInflater)
            })
            .create()
    }
}

class HeightAdaptiveViewPager(context: Context, attributeSet: AttributeSet?) : ViewPager(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var newHeightMeasureSpec = heightMeasureSpec

        if (measureMode == View.MeasureSpec.UNSPECIFIED || measureMode == View.MeasureSpec.AT_MOST) {
            var maxChildHeight = 0
            for (i in 0 until childCount) {
                getChildAt(i).apply {
                    measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                    if (measuredHeight > maxChildHeight) {
                        maxChildHeight = measuredHeight
                    }
                }
            }

            newHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxChildHeight, View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

}

// TODO: разобраться с размером
class MyPagerAdapter(private val layoutInflater: LayoutInflater) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = when (position) {
            0 -> layoutInflater.inflate(R.layout.reduce_layout, container, false)
            1 -> layoutInflater.inflate(R.layout.reduce_layout2, container, false)
            else -> layoutInflater.inflate(R.layout.reduce_layout3, container, false)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return 3
    }

}
