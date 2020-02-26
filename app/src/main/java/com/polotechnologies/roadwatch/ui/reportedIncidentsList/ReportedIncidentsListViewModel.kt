package com.polotechnologies.roadwatch.ui.reportedIncidentsList

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.polotechnologies.roadwatch.dataModels.Report

class ReportedIncidentsListViewModel(
    private val mDatabase: FirebaseFirestore,
    val application: Application,
    val reportedIncident: String
) : ViewModel() {

    //List from Server
    private val _reportedList = MutableLiveData<List<Report>>()
    val reportedList: LiveData<List<Report>>
        get() = _reportedList

    //selected hero
    private val _dispatchingIncident = MutableLiveData<Report>()
    val dispatchingIncident : LiveData<Report>
        get () = _dispatchingIncident


    init {
        fetchReportedCases()
    }

    private fun fetchReportedCases() {
        val query = mDatabase.collection("reportedIncidents")
            .whereEqualTo("incident", reportedIncident)
            .whereEqualTo("acted_upon", false)

        val reportedCases = query.addSnapshotListener { querySnapshot, exception ->
            val reportedCasesList: ArrayList<Report> = arrayListOf()

            if (exception != null) {
                Log.w("@@@Fetching Case@@@", "Listen failed.", exception)
                return@addSnapshotListener
            } else {

                for (document in querySnapshot!!.documents) {
                    val reportedCase = document.toObject(Report::class.java)
                    reportedCasesList.add(reportedCase!!)
                    _reportedList.value = reportedCasesList
                }
            }
        }

    }

    fun dispatchingIncident(reportedIncident: Report) {
        _dispatchingIncident.value = reportedIncident
    }


    fun dispatchOfficers(reportedIncident: Report) {
        val data = hashMapOf("acted_upon" to true)

        mDatabase.collection("reportedIncidents")
            .document(reportedIncident.report_key)
            .set(data, SetOptions.merge()).addOnSuccessListener {
                Toast.makeText(application.applicationContext, "Successfully Dispatched", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {exception->
                Toast.makeText(application.applicationContext, "Failed to  Dispatched: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}