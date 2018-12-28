package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.github.andreylitvintsev.usgsearthquakeviewer.R


class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false).apply {
            findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
                fragmentManager?.popBackStack()
            }
        }
    }

}
