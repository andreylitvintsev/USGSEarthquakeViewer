package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.github.andreylitvintsev.usgsearthquakeviewer.R


class HeightAdaptiveViewPager(context: Context, attributeSet: AttributeSet?) : ViewPager(context, attributeSet) {

    private var canSwipe: Boolean = false

    init {
        obtainStyledAttributes(context, attributeSet)
    }

    private fun obtainStyledAttributes(context: Context, attributeSet: AttributeSet?) {
        val styledAttributes = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.HeightAdaptiveViewPager
        )
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
