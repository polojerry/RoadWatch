package com.polotechnologies.roadwatch.ui.reportedIncidentsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.adapters.ReportedIncidentsRecyclerAdapter
import com.polotechnologies.roadwatch.databinding.FragmentReportedIncidentsListBinding

/**
 * A simple [Fragment] subclass.
 */
class ReportedIncidentsListFragment : Fragment() {

    private lateinit var mBinding : FragmentReportedIncidentsListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reported_incidents_list, container, false)

        setUpListeners()

        val adapter = ReportedIncidentsRecyclerAdapter()
        mBinding.recyclerReportedList.adapter = adapter


        return mBinding.root
    }
    
    private fun setUpListeners() {
        mBinding.toolbarReportedRoadIncidentsList.setNavigationOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        activity!!.onBackPressed()
    }

}
