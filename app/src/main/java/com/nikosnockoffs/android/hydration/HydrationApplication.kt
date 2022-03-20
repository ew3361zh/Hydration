package com.nikosnockoffs.android.hydration

import android.app.Application

class HydrationApplication: Application() {

    private val database by lazy { WaterDatabase.getDatabase(this) }

    // activity can access this repository
    val waterRepository by lazy { WaterRepository(database.waterDao())}
    val daysRepository = DaysRepository()

}