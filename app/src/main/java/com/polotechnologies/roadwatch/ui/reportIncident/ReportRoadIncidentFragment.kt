package com.polotechnologies.roadwatch.ui.reportIncident


import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.databinding.FragmentReportRoadIncidentBinding

/**
 * A simple [Fragment] subclass.
 */
class ReportRoadIncidentFragment : Fragment() {
    
    private lateinit var mBinding: FragmentReportRoadIncidentBinding

    private var reportingIncident = ""
    private var areaCounty = ""
    private var typeOfVehicle = ""
    private var vehicleNumberPlate = ""
    private var vehicleDestination = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_road_incident, container, false)
        
        setClickListeners()
        intentFromIncidents()
        
        return mBinding.root
    }

    private fun intentFromIncidents() {
        val args = ReportRoadIncidentFragmentArgs.fromBundle(arguments!!)
        reportingIncident = args.roadIncident
        mBinding.etIncident.setText(reportingIncident)
    }

    private fun setClickListeners() {
        mBinding.toolbarReportIncidents.setNavigationOnClickListener { 
            activity!!.onBackPressed()
        }
        
        mBinding.buttonReportIncident.setOnClickListener { 
            report()
        }
    }

    private fun report() {
        if(validateInputs()){
            reportIncident()
        }
    }

    private fun validateInputs(): Boolean {

        var isValid = false

        areaCounty = mBinding.etAreaCounty.text.toString()
        typeOfVehicle = mBinding.etTypeOfVehicle.text.toString()
        vehicleNumberPlate = mBinding.etVehicleNumberPlate.text.toString()
        vehicleDestination = mBinding.etVehicleDestination.text.toString()


        if (areaCounty == "") {
            isValid = false
            mBinding.etAreaCounty.error = "Area/County Required"
        }

        if (typeOfVehicle == "") {
            isValid = false
            mBinding.etTypeOfVehicle.error = "Type of Vehicle Required"
        }

        if (vehicleNumberPlate == "") {
            isValid = false
            mBinding.etVehicleNumberPlate.error = "Vehicle Number Required"
        }

        if (vehicleDestination == "") {
            isValid = false
            mBinding.etVehicleDestination.error = "Vehicle Destination Required"
        }


        if (areaCounty != "" && typeOfVehicle != "" && vehicleNumberPlate != "" && vehicleDestination != "") isValid = true

        return isValid

    }

    private fun reportIncident() {
        Toast.makeText(context, "Reporting $reportingIncident.....", Toast.LENGTH_SHORT).show()

    }


}
