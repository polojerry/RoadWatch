package com.polotechnologies.roadwatch.ui.roadIncidents


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


        return mBinding.root
    }

    private fun inflateMenu() {
        mBinding.toolbarRoadIncidents.inflateMenu(R.menu.menu_main)
        mBinding.toolbarRoadIncidents.setOnMenuItemClickListener(this)
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
        findNavController().navigate(R.id.action_roadIncidentsFragment_to_loginFragment)

    }


}
