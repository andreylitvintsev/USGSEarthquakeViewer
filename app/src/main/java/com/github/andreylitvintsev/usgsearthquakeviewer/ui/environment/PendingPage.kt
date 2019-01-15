package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import kotlin.properties.Delegates.observable


abstract class PendingPage(val context: Context,val commandDelayer: CommandDelayer) {

    private companion object {
        const val EMPTY_PLACEHOLDER = -1
    }

    private var placeholderView: View? = null
    private var mainView: View? = null

    private var asyncShowCommand by observable<CommandWithDelay?>(null) { _, _, newValue ->
        if (newValue != null) tryHandleShowCommand()
    }

    private val viewGroup = FrameLayout(context).apply {
        if (getPlaceholderLayout() != EMPTY_PLACEHOLDER) {
            placeholderView = View.inflate(context, getPlaceholderLayout(), null)
            addView(placeholderView)
        }
    }

    fun showPlaceHolder(delay: Long = 0L) {
        asyncShowCommand = CommandWithDelay(Runnable {
            mainView?.visibility = View.GONE
            placeholderView?.visibility = View.VISIBLE
        }, delay)
    }

    fun showMainView(delay: Long = 0L) {
        asyncShowCommand = CommandWithDelay(Runnable {
            placeholderView?.visibility = View.GONE
            mainView?.visibility = View.VISIBLE
        }, delay)
    }

    private fun tryHandleShowCommand() {
        if (mainView != null) {
            asyncShowCommand?.let { commandDelayer.launch(it) }
            asyncShowCommand = null
        }
    }

    internal fun onInstantiateItem(container: ViewGroup, position: Int): View {
        onInstantiatePage(position)

        if (mainView == null) {
            AsyncLayoutInflater(context).inflate(getLayout(), viewGroup) { view, i, viewGroup ->
                mainView = view
                view.visibility = View.GONE
                onViewInflated(view)
                viewGroup?.addView(view)
            }
        }

        return viewGroup
    }

    @LayoutRes
    protected open fun getPlaceholderLayout(): Int = EMPTY_PLACEHOLDER

    @LayoutRes
    protected abstract fun getLayout(): Int

    protected open fun onInstantiatePage(pageIndex: Int) {
        // Do nothing!
    }

    @CallSuper
    protected open fun onViewInflated(view: View) {
        tryHandleShowCommand()
    }

    internal open fun onPageStayVisible(pageIndex: Int) {
        // Do nothing!
    }

    internal open fun onLeavedPage() {
        // Do nothing!
    }

}
