package com.github.andreylitvintsev.usgsearthquakeviewer

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: может ли существовать фрагмент без активити?
class FilterDialogFragment : AppCompatDialogFragment() { // TODO: посмотреть в чем разница между DialogFragment

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(appCompatActivity())
            .setView(appCompatActivity().layoutInflater.inflate(R.layout.dialog_fragment_filter, null).apply {
                val viewPager = this.findViewById<ViewPager>(R.id.viewPager)
                viewPager.adapter = object : FragmentPagerAdapter(appCompatActivity().supportFragmentManager) {
                    override fun getItem(p0: Int): Fragment = when (p0) {
//                        0 -> MagnitudeRangePickerFragment()
                        else -> DatePickerFragment()
                    }
//
                    override fun getCount(): Int = 2
                }
            })
            .create()
    }
}

class MagnitudeRangePickerFragment : Fragment() {

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return View(context).apply {
//            setBackgroundColor(Color.RED)
//            layoutParams =
//                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        }
//    }
}

class DatePickerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.reduce_layout, container, false)
    }

}