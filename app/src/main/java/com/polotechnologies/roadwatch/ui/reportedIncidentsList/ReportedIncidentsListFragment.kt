package com.polotechnologies.roadwatch.ui.reportedIncidentsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.adapters.ReportedIncidentsRecyclerAdapter
import com.polotechnologies.roadwatch.databinding.FragmentReportedIncidentsListBinding

/**
 * A simple [Fragment] subclass.
 */
class ReportedIncidentsListFragment : Fragment() {

    private lateinit var mBinding : FragmentReportedIncidentsListBinding
    private lateinit var mDatabase: FirebaseFirestore
    private lateinit var mViewModel: ReportedIncidentsListViewModel

    private var reportingIncident = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reported_incidents_list, container, false)
        mDatabase = FirebaseFirestore.getInstance()
        setUpListeners()
        intentFromIncidents()

        val factory = ReportedIncidentsListViewModelFactory(mDatabase, activity!!.application,reportingIncident )
        mViewModel = ViewModelProvider(this, factory).get(ReportedIncidentsListViewModel::class.java)

        val adapter = ReportedIncidentsRecyclerAdapter(ReportedIncidentsRecyclerAdapter.OnClickListener{reportedIncident->
            mViewModel.dispatchingIncident(reportedIncident)
        })
        mBinding.recyclerReportedList.adapter = adapter

        mViewModel.reportedList.observe(viewLifecycleOwner, Observer {reportedIncidents->
            adapter.submitList(reportedIncidents)
        })

        mViewModel.dispatchingIncident.observe(viewLifecycleOwner, Observer {dispatchingIncidents->
            mViewModel.dispatchOfficers(dispatchingIncidents)
        })

        return mBinding.root
    }

    private fun intentFromIncidents() {
        val args = ReportedIncidentsListFragmentArgs.fromBundle(arguments!!)
        reportingIncident = args.incident
        mBinding.toolbarReportedRoadIncidentsList.title = reportingIncident
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
