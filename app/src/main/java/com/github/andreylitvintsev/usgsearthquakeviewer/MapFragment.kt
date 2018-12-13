package com.github.andreylitvintsev.usgsearthquakeviewer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
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
            Log.d("TAG", "filter")
            true
        }
        R.id.about -> {
            openAboutFragment()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun openAboutFragment() {
        fragmentManager?.beginTransaction()?.add(android.R.id.content, AboutFragment())?.commit()
    }

}
