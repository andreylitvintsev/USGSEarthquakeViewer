package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.appCompatActivity
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.PendingPagerAdapter
import java.util.*


// TODO: может ли существовать фрагмент без активити?

class FilterDialogFragment : AppCompatDialogFragment() { // TODO: посмотреть в чем разница между DialogFragment

    private lateinit var viewPager: ViewPager
    private val date = Date() // TODO: сделать сохранение на поворотах

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(appCompatActivity())
            .setView(appCompatActivity().layoutInflater.inflate(R.layout.dialog_fragment_filter, null).apply {
                viewPager = findViewById(R.id.viewPager)
                viewPager.adapter = createPagerAdapter()
            })
            .create()
    }

    private fun createPagerAdapter() = PendingPagerAdapter(
        MagnitudeFilterPage(viewPager, appCompatActivity(), date),
        DateFilterPage(viewPager, appCompatActivity(), date)
    )

}
