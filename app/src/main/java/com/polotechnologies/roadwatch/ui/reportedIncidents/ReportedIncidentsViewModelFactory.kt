package com.polotechnologies.roadwatch.ui.reportedIncidents

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore

class ReportedIncidentsViewModelFactory (private val database: FirebaseFirestore,
                                         private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReportedIncidentsViewModel::class.java)) {
                return ReportedIncidentsViewModel(
                    database,
                    application
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}