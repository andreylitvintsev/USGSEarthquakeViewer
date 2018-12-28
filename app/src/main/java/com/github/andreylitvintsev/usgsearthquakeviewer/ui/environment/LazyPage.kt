package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.content.Context
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes


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

    fun showPlaceHolder() {
        mainView?.visibility = View.GONE
        placeholderView?.visibility = View.VISIBLE
    }

    fun showMainView() {
        placeholderView?.visibility = View.GONE
        mainView?.visibility = View.VISIBLE
    }

    @LayoutRes
    protected open fun getPlaceholderLayout(): Int = EMPTY_PLACEHOLDER

    @LayoutRes
    protected abstract fun getLayout(): Int

    protected open fun onViewInflated(view: View) {
        // Do nothing!
    }

    internal open fun onLeavedPage() {
        // Do nothing!
    }

    @CallSuper
    open fun onPageStayVisible(pageIndex: Int) {
        if (mainView == null) {
            AsyncLayoutInflater(context).inflate(getLayout(), viewGroup) { view, i, viewGroup ->
                placeholderView?.visibility = View.GONE

                mainView = view
                onViewInflated(view)
                viewGroup?.addView(view)
            }
        }
    }

    internal fun onInstantiateItem(container: ViewGroup, position: Int): View = viewGroup

}
