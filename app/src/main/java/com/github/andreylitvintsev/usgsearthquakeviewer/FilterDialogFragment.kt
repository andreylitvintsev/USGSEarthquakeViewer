package com.github.andreylitvintsev.usgsearthquakeviewer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.view.AsyncLayoutInflater
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


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

    private fun createPagerAdapter(): LazyPagerAdapter {
        val page = LazyPageImpl(appCompatActivity())
        val page1 = LazyPageImpl(appCompatActivity())
        val page2 = LazyPageImplWithPH(appCompatActivity())
        val page3 = LazyPageImpl(appCompatActivity())
        val page4 = LazyPageImplWithPH(appCompatActivity())
        return LazyPagerAdapter(page, page1, page2, page3, page4)
    }

}

// TODO: вынести в отдельный пакет
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

// TODO: вынести в отдельный пакет
class LazyPagerAdapter(vararg val pages: LazyPage) : PagerAdapter() { // TODO: вынести в отдельный пакет

    private var currentPage: Int = -1

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)

        if (currentPage != position) {
            currentPage = position
            pages[currentPage].onPageStayVisible(currentPage)
            Log.d("TAG", "blocking!")
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

// TODO: вынести в отдельный пакет
abstract class LazyPage(val context: Context) {

    private companion object {
        const val EMPTY_PLACEHOLDER = -1
    }

    private var placeholderView: View? = null
    private var mainView: View? = null

    private val viewGroup = FrameLayout(context).apply {
        if (getPlaceholderLayout() != EMPTY_PLACEHOLDER) {
            placeholderView = View.inflate(context, getPlaceholderLayout(), null)
            addView(placeholderView)
        }
    }

    @LayoutRes
    protected open fun getPlaceholderLayout(): Int = EMPTY_PLACEHOLDER

    @LayoutRes
    protected abstract fun getLayout(): Int

    protected open fun onViewInflated(view: View) {
        // Do nothing!
    }

    @CallSuper
    open fun onPageStayVisible(pageIndex: Int) {
        if (mainView == null) {
            AsyncLayoutInflater(context).inflate(R.layout.page_dialog_filter, viewGroup) { view, i, viewGroup ->
                viewGroup?.removeView(placeholderView)
                placeholderView = null

                mainView = view
                mainView?.let { onViewInflated(it) }
                viewGroup?.addView(view)
            }
        }
    }

    internal fun onInstantiateItem(container: ViewGroup, position: Int): View = viewGroup

}

class LazyPageImpl(context: Context) : LazyPage(context) {

    override fun getLayout(): Int = R.layout.page_dialog_datepicker

    override fun onViewInflated(view: View) {
        Log.d("TAG", "LazyPageImpl:onViewInflated")
    }

}

class LazyPageImplWithPH(context: Context) : LazyPage(context) {

    override fun getPlaceholderLayout(): Int = R.layout.stub

    override fun getLayout(): Int = R.layout.page_dialog_datepicker

    override fun onViewInflated(view: View) {
        Log.d("TAG", "LazyPageImplWithPH:onViewInflated")
    }

}
