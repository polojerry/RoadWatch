package com.polotechnologies.roadwatch.ui.reportedIncidentsList

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
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

    init {
        fetchReportedCases()
    }

    private fun fetchReportedCases() {
        val query = mDatabase.collection("reportedIncidents")
            .whereEqualTo("incident", reportedIncident)

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
}