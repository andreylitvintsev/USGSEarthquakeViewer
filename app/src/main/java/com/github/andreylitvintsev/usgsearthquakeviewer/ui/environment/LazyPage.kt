package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.view.AsyncLayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.andreylitvintsev.usgsearthquakeviewer.R


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
    protected open fun getPlaceholderLayout(): Int =
        EMPTY_PLACEHOLDER

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
