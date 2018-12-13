package com.github.andreylitvintsev.usgsearthquakeviewer

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


fun Fragment.openFragment(
    fragment: Fragment, tag: String? = null, addToBackStack: Boolean = false, backStackName: String? = null
) {
    (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        .add(android.R.id.content, fragment, tag)
        .apply { if (addToBackStack) addToBackStack(backStackName) }
        .commit()
}