package com.github.andreylitvintsev.usgsearthquakeviewer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false).apply {
            findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
                fragmentManager?.popBackStack()
            }
        }
    }

}
