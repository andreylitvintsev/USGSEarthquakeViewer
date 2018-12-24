package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.util.Log
import android.view.View
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.appCompatActivity
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.LazyPage
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.LazyPagerAdapter


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
