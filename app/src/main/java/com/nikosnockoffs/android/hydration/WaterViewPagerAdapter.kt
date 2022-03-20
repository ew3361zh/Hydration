package com.nikosnockoffs.android.hydration

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// adapter to provide views for the view pager
class WaterViewPagerAdapter(activity: FragmentActivity, private val days: List<String>):
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return days.size
    }

    override fun createFragment(position: Int): Fragment {
        val day = days[position]
        return HydrationFragment.newInstance(day)
    }

}