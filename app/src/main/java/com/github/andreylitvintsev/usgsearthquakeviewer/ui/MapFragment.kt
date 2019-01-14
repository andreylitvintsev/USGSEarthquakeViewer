package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.appCompatActivity
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.openFragment
import com.google.android.gms.maps.GoogleMap

class MapFragment : Fragment() {

    private lateinit var map: GoogleMap



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false).apply {
            setHasOptionsMenu(true)
            (activity as AppCompatActivity).setSupportActionBar(findViewById(R.id.toolbar))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.eventsFilter -> {
            openFilterDialogFragment()
            true
        }
        R.id.about -> {
            openAboutFragment()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun openFilterDialogFragment() {
        FilterDialogFragment()
            .show(appCompatActivity().supportFragmentManager, "dialogFragment")
    }

    private fun openAboutFragment() {
        openFragment(AboutFragment(), addToBackStack = true)
    }

}
