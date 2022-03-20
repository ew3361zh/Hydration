package com.nikosnockoffs.android.hydration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RatingBar

private const val ARG_DAY_OF_WEEK = "day_of_week"

class HydrationFragment : Fragment() {

    private lateinit var dayOfWeek: String
    private lateinit var waterRatingBar: RatingBar
    private lateinit var addGlassButton: ImageButton
    private lateinit var resetGlassesButton: ImageButton
    private lateinit var waterRecord: WaterRecord

    private val waterViewModel: WaterViewModel by lazy { // fragments don't have direct access to the application
        val app = requireActivity().application as HydrationApplication
        WaterViewModelFactory(app.waterRepository, app.daysRepository)
            .create(WaterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dayOfWeek = it.getString(ARG_DAY_OF_WEEK)!! // not null assertion, want prog to crash if we get null value for DOW

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hydration, container, false)

        waterRatingBar = view.findViewById(R.id.water_rating_bar)
        addGlassButton = view.findViewById(R.id.add_glass_button)
        resetGlassesButton = view.findViewById(R.id.reset_count_button)

        waterRatingBar.numStars = WaterRecord.MAX_GLASSES
        waterRatingBar.numStars = WaterRecord.MAX_GLASSES

        // when record for day changes, this observer is called
        // also called first time this is called
        waterViewModel.getRecordForDay(dayOfWeek).observe(requireActivity()) { wr ->
            if (wr == null) {
                waterViewModel.insertNewRecord(WaterRecord(dayOfWeek, 0))
            } else {
                waterRecord = wr
                waterRatingBar.progress = waterRecord.glasses


                // putting event listeners here so they are only activated when there's data available

                addGlassButton.setOnClickListener {
                    addGlass()
                }

                resetGlassesButton.setOnClickListener {
                    resetGlasses()
                }

            }
        }

        return view
    }

    private fun addGlass() {
        // add 1 to total glasses - up to max of 5
        if (waterRecord.glasses < WaterRecord.MAX_GLASSES) {
            waterRecord.glasses++
            waterViewModel.updateRecord(waterRecord) // update water record with changed data
        }
    }

    private fun resetGlasses() {
        waterRecord.glasses = 0
        waterViewModel.updateRecord(waterRecord)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HydrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(dayOfWeek: String) = // companion object - always want to create a fragment that knows DOW
            HydrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DAY_OF_WEEK, dayOfWeek)
                }
            }
    }
}