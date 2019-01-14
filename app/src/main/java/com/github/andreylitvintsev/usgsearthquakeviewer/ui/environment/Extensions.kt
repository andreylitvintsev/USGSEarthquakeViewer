package com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*


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


// TODO: может вынести в отельный файл?
fun Date.toFormattedString(format: String = "yyyy.MM.dd"): String {
    return SimpleDateFormat(format, Locale.US).format(this)
}
