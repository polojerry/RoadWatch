package com.polotechnologies.roadwatch.ui.reportedIncidents

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.dataModels.Report
import com.polotechnologies.roadwatch.databinding.FragmentReportedIncidentsBinding

/**
 * A simple [Fragment] subclass.
 */
class ReportedIncidentsFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private lateinit var mBinding : FragmentReportedIncidentsBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDatabase: FirebaseFirestore
    private lateinit var mViewModel: ReportedIncidentsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reported_incidents, container, false)
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseFirestore.getInstance()

        val factory = ReportedIncidentsViewModelFactory(mDatabase, activity!!.application)
        mViewModel = ViewModelProvider(this, factory).get(ReportedIncidentsViewModel::class.java)

        inflateMenu()
        setObservers()
        setClickListeners()
        return mBinding.root
    }

    private fun setObservers() {
        mViewModel.overSpeedingCounter.observe(viewLifecycleOwner, Observer {overSpeedingCounter->
            if(overSpeedingCounter > 0) mBinding.badgeOverSpeeding.visibility = View.VISIBLE
            mBinding.badgeOverSpeeding.text = overSpeedingCounter.toString()
        })

        mViewModel.overLoadingCounter.observe(viewLifecycleOwner, Observer {overLoadingCounter->
            if(overLoadingCounter > 0) mBinding.badgeOverLoading.visibility = View.VISIBLE
            mBinding.badgeOverLoading.text = overLoadingCounter.toString()
        })

        mViewModel.drunkenDrivingCounter.observe(viewLifecycleOwner, Observer {drunkenDrivingCounter->
            if(drunkenDrivingCounter > 0) mBinding.badgeDrunkenDriving.visibility = View.VISIBLE
            mBinding.badgeDrunkenDriving.text = drunkenDrivingCounter.toString()
        })

        mViewModel.carelessOvertakingCounter.observe(viewLifecycleOwner, Observer {carelessOvertakingCounter->
            if(carelessOvertakingCounter > 0) mBinding.badgeCarelessOvertaking.visibility = View.VISIBLE
            mBinding.badgeCarelessOvertaking.text = carelessOvertakingCounter.toString()
        })

        mViewModel.unRoadWorthyVehicleCounter.observe(viewLifecycleOwner, Observer {unRoadWorthyVehicleCounter->
            if(unRoadWorthyVehicleCounter > 0) mBinding.badgeUnRoadWorthyVehicle.visibility = View.VISIBLE
            mBinding.badgeUnRoadWorthyVehicle.text = unRoadWorthyVehicleCounter.toString()
        })
    }

    private fun setClickListeners() {
        mBinding.cardOverSpeeding.setOnClickListener {
            navigateToReport("Over Speeding")
        }
        mBinding.cardOverLoading.setOnClickListener {
            navigateToReport("Over Loading")
        }
        mBinding.cardDrunkenDriving.setOnClickListener {
            navigateToReport("Drunken Driving")
        }
        mBinding.cardCarelessOvertaking.setOnClickListener {
            navigateToReport("Careless Overtaking")
        }
        mBinding.cardUnRoadworthyVehicle.setOnClickListener {
            navigateToReport("Un-Roadworthy Vehicle")
        }

    }

    private fun navigateToReport(roadIncident: String) {
        val action = ReportedIncidentsFragmentDirections.actionReportedIncidentsFragmentToReportedIncidentsListFragment(roadIncident)
        findNavController().navigate(action)
    }

    private fun inflateMenu() {
        mBinding.toolbarReportedRoadIncidents.inflateMenu(R.menu.menu_main)
        mBinding.toolbarReportedRoadIncidents.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_sign_out -> signOut()
        }
        return true
    }

    private fun signOut() {
        savePreferenceUserAccount()
        mAuth.signOut()
        Toast.makeText(context, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_roadIncidentsFragment_to_loginFragment)
    }

    private fun savePreferenceUserAccount() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.user_account_type), "null")
            commit()
        }

    }


}
