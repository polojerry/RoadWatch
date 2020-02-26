package com.polotechnologies.roadwatch.ui.reportIncident


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.dataModels.Report
import com.polotechnologies.roadwatch.databinding.FragmentReportRoadIncidentBinding

/**
 * A simple [Fragment] subclass.
 */
class ReportRoadIncidentFragment : Fragment() {
    
    private lateinit var mBinding: FragmentReportRoadIncidentBinding
    private lateinit var mDatabase: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

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
        mDatabase = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        
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
            navigateBack()
        }
        
        mBinding.buttonReportIncident.setOnClickListener { 
            report()
        }
    }

    private fun navigateBack() {
        activity!!.onBackPressed()
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
        Toast.makeText(context, "Reporting $reportingIncident.....", Toast.LENGTH_LONG).show()
        mBinding.buttonReportIncident.isEnabled = false

        val reportRef =mDatabase.collection("reportedIncidents").document()

        val incident = Report(
            mAuth.currentUser!!.uid,
            reportRef.id,
            reportingIncident,
            areaCounty,
            typeOfVehicle,
            vehicleNumberPlate,
            vehicleDestination,
            Timestamp.now()
        )


        reportRef.set(incident)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context, "Successfully reported: $reportingIncident  ", Toast.LENGTH_SHORT).show()
                    navigateBack()

                }
            }.addOnFailureListener {exception->
                Toast.makeText(context, "Failed to Report Incident : ${exception.message}", Toast.LENGTH_SHORT).show()
            }

    }

}
