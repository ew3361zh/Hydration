package com.nikosnockoffs.android.hydration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    // watervmfactory is given application context but needs specific subclass iteration
    // because general applications don't know about the repository but hydrationapplication does
    private val waterViewModel by lazy {
        WaterViewModelFactory((application as HydrationApplication).repository)
            .create(WaterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tuesday = WaterRecord("Tuesday", 4)
        waterViewModel.insertNewRecord(tuesday)

        val thursday = WaterRecord("Thursday", 3)
        waterViewModel.insertNewRecord(thursday)

        waterViewModel.allRecords.observe(this) { records ->
            Log.d("MAIN_ACTIVITY", "$records")

        }

    }
}