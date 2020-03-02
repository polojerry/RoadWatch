package com.polotechnologies.roadwatch.ui.roadIncidents


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
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.databinding.FragmentRoadIncidentsBinding

/**
 * A simple [Fragment] subclass.
 */
class RoadIncidentsFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private lateinit var mBinding: FragmentRoadIncidentsBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_road_incidents, container, false)
        mAuth = FirebaseAuth.getInstance()

        inflateMenu()
        setClickListeners()


        return mBinding.root
    }

    private fun inflateMenu() {
        mBinding.toolbarRoadIncidents.inflateMenu(R.menu.menu_main)
        mBinding.toolbarRoadIncidents.setOnMenuItemClickListener(this)
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
        val action = RoadIncidentsFragmentDirections.actionRoadIncidentsFragmentToReportRoadIncidentFragment(roadIncident)
        findNavController().navigate(action)
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
