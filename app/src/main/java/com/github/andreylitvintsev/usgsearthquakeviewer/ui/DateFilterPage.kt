package com.github.andreylitvintsev.usgsearthquakeviewer.ui

import android.content.Context
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.github.andreylitvintsev.usgsearthquakeviewer.R
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.CommandDelayer
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.PendingPage
import com.github.andreylitvintsev.usgsearthquakeviewer.ui.environment.toFormattedString
import kotlinx.android.synthetic.main.fragment_about.view.*
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.util.*


class DateFilterPage(
    val viewPager: ViewPager,
    context: Context,
    commandDelayer: CommandDelayer,
    val date: Date
) : PendingPage(context, commandDelayer) {

    override fun getPlaceholderLayout(): Int = R.layout.placeholder_dialog_filter

    override fun getLayout(): Int = R.layout.page_dialog_datepicker

    override fun onInstantiatePage(pageIndex: Int) = showPlaceHolder()

    override fun onPageStayVisible(pageIndex: Int) = showMainView(delay = 300)

    override fun onLeavedPage() = showPlaceHolder()


    override fun onViewInflated(rootView: View) {
        super.onViewInflated(rootView)

        initViews(rootView)
    }

    private fun initViews(rootView: View) {
        val toolbar = initToolbar(rootView)
        initCalendarView(rootView, toolbar)
    }

    private fun initToolbar(rootView: View): Toolbar {
        return rootView.findViewById<Toolbar>(R.id.toolbar).apply {
            toolbar.title = date.toFormattedString()

            setNavigationOnClickListener {
                toPreviousPage()
            }
        }
    }

    private fun initCalendarView(rootView: View, toolbar: Toolbar): CalendarView {
        return rootView.findViewById<CalendarView>(R.id.calendarView).apply {
            val calendar = Calendar.getInstance()

            // Initial date
            val initialDate = CalendarDate(date)

            // Maximum available date
            val maxDate = CalendarDate(calendar.time)

            // Minimum available date
            calendar.set(1970, Calendar.JANUARY, 1)
            val minDate = CalendarDate(calendar.time)

            // The first day of week
            val firstDayOfWeek = java.util.Calendar.MONDAY

            // Set up calendar with all available parameters
            setupCalendar(
                initialDate = initialDate,
                minDate = minDate,
                maxDate = maxDate,
                selectionMode = CalendarView.SelectionMode.SINGLE,
                firstDayOfWeek = firstDayOfWeek,
                showYearSelectionView = false,
                selectedDates = listOf(initialDate)
            )

            onDateClickListener = { calendarDate ->
                date.time = calendarDate.date.time
                toolbar.title = calendarDate.date.toFormattedString()
            }
        }
    }

    private fun toPreviousPage() {
        viewPager.currentItem -= 1
    }

}
