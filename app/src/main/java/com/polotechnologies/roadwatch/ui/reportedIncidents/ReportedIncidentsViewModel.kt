package com.polotechnologies.roadwatch.ui.reportedIncidents

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.polotechnologies.roadwatch.dataModels.Report

class ReportedIncidentsViewModel(
    private val mDatabase: FirebaseFirestore,
    val application: Application
) : ViewModel() {

    private val _overSpeedingCounter = MutableLiveData<Int>()
    val overSpeedingCounter: LiveData<Int>
        get() = _overSpeedingCounter

    private val _overLoadingCounter = MutableLiveData<Int>()
    val overLoadingCounter: LiveData<Int>
        get() = _overLoadingCounter

    private val _drunkenDrivingCounter = MutableLiveData<Int>()
    val drunkenDrivingCounter: LiveData<Int>
        get() = _drunkenDrivingCounter

    private val _carelessOvertakingCounter = MutableLiveData<Int>()
    val carelessOvertakingCounter: LiveData<Int>
        get() = _carelessOvertakingCounter

    private val _unRoadWorthyVehicleCounter = MutableLiveData<Int>()
    val unRoadWorthyVehicleCounter: LiveData<Int>
        get() = _unRoadWorthyVehicleCounter

    init {
        fetchReportedCases()
    }

    private fun fetchReportedCases() {
        val query = mDatabase.collection("reportedIncidents")
            .whereEqualTo("acted_upon", false)

        val reportedCases = query.addSnapshotListener { querySnapshot, exception ->
            var counterOverSpeeding = 0
            var counterLoadingCounter = 0
            var counterDrunkenDriving = 0
            var counterCarelessOvertaking = 0
            var counterUnRoadWorthyVehicle = 0


            if (exception != null) {
                Log.w("@@@Fetching Case@@@", "Listen failed.", exception)
                return@addSnapshotListener
            } else {

                for (document in querySnapshot!!.documents) {
                    val reportedCase = document.toObject(Report::class.java)
                    when (reportedCase!!.incident) {
                        "Over Speeding" -> {
                            counterOverSpeeding += 1
                            _overSpeedingCounter.value = counterOverSpeeding
                        }
                        "Over Loading" -> {
                            counterLoadingCounter += 1
                            _overLoadingCounter.value = counterLoadingCounter
                        }
                        "Drunken Driving" -> {
                            counterDrunkenDriving += 1
                            _drunkenDrivingCounter.value = counterDrunkenDriving
                        }
                        "Careless Overtaking" -> {
                            counterCarelessOvertaking += 1
                            _carelessOvertakingCounter.value = counterCarelessOvertaking
                        }
                        "Un-Roadworthy Vehicle" -> {
                            counterUnRoadWorthyVehicle += 1
                            _unRoadWorthyVehicleCounter.value = counterUnRoadWorthyVehicle
                        }
                    }

                }
            }
        }

    }
}