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
import android.view.ViewStub
import android.widget.FrameLayout


// TODO: может ли существовать фрагмент без активити?

// TODO: реализовать PendingView
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

    private fun createPagerAdapter() = LazyPagerAdapter(LazyPageImpl(appCompatActivity()))

//    private fun createPagerAdapter() = SimplePagerAdapter(1) { container, position ->
//        val layoutId = when (position) {
//            0 -> R.layout.page_dialog_filter
//            else -> throw IllegalArgumentException("The number of pages more than the number of layouts!")
//        }
//
//        return@SimplePagerAdapter appCompatActivity().layoutInflater.inflate(layoutId, container, false).apply {
//            when (position) {
////                0 -> configureFilterPage(this)
//                0 -> configureDatePickerPage(this)
//                else -> throw IllegalArgumentException("The number of pages more than the number of layouts!")
//            }
//        }
//    }

//    private fun configureFilterPage(view: View) {
//        view.findViewById<Button>(R.id.timePickerButton).setOnClickListener {
//            viewPager.currentItem = 1
//        }
//    }

//    private fun configureDatePickerPage(view: View) {
//        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
//        toolbar.setNavigationOnClickListener {
//            viewPager.currentItem = 0
//        }
//    }

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


class LazyPagerAdapter(private val lazyPage: LazyPage) : PagerAdapter() { // TODO: массив страниц

    private var currentPage: Int = -1

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)

        if (currentPage != position) {
            currentPage = position
            lazyPage.onPageStayVisible(currentPage)
            Log.d("TAG", "blocking!")
        }


    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewOnPosition = lazyPage.onInstantiateItem(container, position)
        container.addView(viewOnPosition)

        return viewOnPosition
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun isViewFromObject(view: View, any: Any) = (view == any)

    override fun getCount(): Int = 2 // TODO

}


abstract class LazyPage(val context: Context) { // TODO: doing!!! + избавиться от перересовки

    private val viewStub = ViewStub(context).apply {
        this.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        this.layoutResource = getLayout()
    }

    private val viewGroup = FrameLayout(context).apply {
//        addView(viewStub)
        Log.d("TAG", "ascnk")
        addView(View.inflate(context, getPlaceholderLayout(), null))
    }

    @LayoutRes
    abstract fun getPlaceholderLayout(): Int

    @LayoutRes
    abstract fun getLayout(): Int

    @CallSuper
    open fun onPageStayVisible(pageIndex: Int) {
        Log.d("TAG", "pagestayVisible: $pageIndex")
//        viewStub.inflate()
        AsyncLayoutInflater(context).inflate(R.layout.page_dialog_filter, viewGroup) { view, i, viewGroup ->
            viewGroup?.addView(view)
        }
    }

    fun onInstantiateItem(container: ViewGroup, position: Int): View = viewGroup

}

class LazyPageImpl(context: Context) : LazyPage(context) {

    override fun getPlaceholderLayout(): Int = R.layout.stub

    override fun getLayout(): Int = R.layout.page_dialog_datepicker

}
