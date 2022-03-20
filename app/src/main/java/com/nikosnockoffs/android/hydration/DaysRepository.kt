package com.nikosnockoffs.android.hydration

import java.text.DateFormatSymbols
import java.util.*

class DaysRepository {

    val weekdays: List<String>
        get() {
            val dateFormatSymbols = DateFormatSymbols.getInstance(Locale.getDefault())
            // dfs.weekdays is an 8 element array, first element is blank, e.g. { ,Sun, Mon, Tue, Wed, Thu, Fri, Sat }
            // On a system that uses another language, the names of the days would be in that language.
            // so days can be numbered starting at 1.
            // Filter out first blank day, use 0-based array of days.
            return dateFormatSymbols.weekdays.asList().filter { it.isNotBlank() }
        }

    val todayIndex: Int
        get() {
            // Returns 0-based index of day
            val today = Calendar.getInstance(Locale.getDefault())
            //Returns a number, no name, 1-based so first day of the week is 1
            val day = today.get(Calendar.DAY_OF_WEEK)
            return day - 1
        }
}