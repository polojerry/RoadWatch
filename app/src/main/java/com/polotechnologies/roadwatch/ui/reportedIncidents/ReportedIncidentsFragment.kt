package com.polotechnologies.roadwatch.ui.reportedIncidents

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
import com.polotechnologies.roadwatch.databinding.FragmentReportedIncidentsBinding

/**
 * A simple [Fragment] subclass.
 */
class ReportedIncidentsFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private lateinit var mBinding : FragmentReportedIncidentsBinding
    private lateinit var mAuth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reported_incidents, container, false)
        mAuth = FirebaseAuth.getInstance()

        inflateMenu()

        return mBinding.root
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
        mAuth.signOut()
        Toast.makeText(context, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_reportedIncidentsFragment_to_loginFragment)
    }

}
