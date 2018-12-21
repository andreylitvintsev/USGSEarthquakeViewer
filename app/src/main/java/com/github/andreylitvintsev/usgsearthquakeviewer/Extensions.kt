package com.github.andreylitvintsev.usgsearthquakeviewer

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity


fun Fragment.appCompatActivity() = (activity as AppCompatActivity)

fun Fragment.openFragment(
    fragment: Fragment, tag: String? = null, addToBackStack: Boolean = false, backStackName: String? = null
) {
    val fm = appCompatActivity().supportFragmentManager
    fm.beginTransaction()
        .add(android.R.id.content, fragment, tag)
        .apply { if (addToBackStack) addToBackStack(backStackName) }
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()
}
