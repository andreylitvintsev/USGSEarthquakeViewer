package com.github.andreylitvintsev.usgsearthquakeviewer

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


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
