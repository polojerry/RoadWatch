package com.polotechnologies.roadwatch.ui.reportedIncidentsList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore

class ReportedIncidentsListViewModelFactory (private val database: FirebaseFirestore,
                                             private val application: Application,
                                             private val incident: String) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReportedIncidentsListViewModel::class.java)) {
                return ReportedIncidentsListViewModel(
                    database,
                    application,
                    incident
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}