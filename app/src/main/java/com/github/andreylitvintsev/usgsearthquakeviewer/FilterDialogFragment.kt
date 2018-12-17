package com.github.andreylitvintsev.usgsearthquakeviewer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button


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

    private fun createPagerAdapter() = SimplePagerAdapter(2) { container, position ->
        val layoutId = when (position) {
            0 -> R.layout.page_dialog_filter
            1 -> R.layout.page_dialog_datepicker
            else -> throw IllegalArgumentException("The number of pages more than the number of layouts!")
        }

        return@SimplePagerAdapter appCompatActivity().layoutInflater.inflate(layoutId, container, false).apply {
            when (position) {
                0 -> configureFilterPage(this)
                1 -> configureDatePickerPage(this)
                else -> throw IllegalArgumentException("The number of pages more than the number of layouts!")
            }
        }
    }

    private fun configureFilterPage(view: View) {
        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewPager.currentItem = 1
        }
    }

    private fun configureDatePickerPage(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            viewPager.currentItem = 0
        }
    }

}


class HeightAdaptiveViewPager(context: Context, attributeSet: AttributeSet?) : ViewPager(context, attributeSet) {

    private var canSwipe: Boolean = false

    init {
        obtainStyledAttributes(context, attributeSet)
    }

    private fun obtainStyledAttributes(context: Context, attributeSet: AttributeSet?) {
        val styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HeightAdaptiveViewPager)
        try {
            canSwipe = styledAttributes.getBoolean(R.styleable.HeightAdaptiveViewPager_canSwipe, true)
        } finally {
            styledAttributes.recycle()
        }
    }

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

    // TODO: разобраться с касанием
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (canSwipe) super.onInterceptTouchEvent(ev) else false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (canSwipe) super.onTouchEvent(ev) else false
    }

}


class SimplePagerAdapter(
    private val pagesNumber: Int,
    private val instantiateItemView: (container: ViewGroup, position: Int) -> View
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewOnPosition = instantiateItemView(container, position)
        container.addView(viewOnPosition)
        return viewOnPosition
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun isViewFromObject(view: View, any: Any) = (view == any)

    override fun getCount() = pagesNumber

}
