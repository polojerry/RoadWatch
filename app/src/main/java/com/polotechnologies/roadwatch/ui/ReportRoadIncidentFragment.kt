package com.polotechnologies.roadwatch.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polotechnologies.roadwatch.R

/**
 * A simple [Fragment] subclass.
 */
class ReportRoadIncidentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_road_incident, container, false)
    }


}