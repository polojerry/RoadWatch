package com.polotechnologies.roadwatch.ui.reportedIncidents

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.polotechnologies.roadwatch.dataModels.Report
import java.lang.Exception

class ReportedIncidentsViewModel(
    private val mDatabase: FirebaseFirestore,
    val application: Application
) : ViewModel() {

    private val _overSpeedingCounter = MutableLiveData<Int>()
    val overSpeedingCounter: LiveData<Int>
        get() = _overSpeedingCounter

    private val _overLoadingCounter = MutableLiveData<LiveData<Int>>()
    val overLoadingCounter: LiveData<Int>
        get() = _overLoadingCounter.value!!

    private val _drunkenDrivingCounter = MutableLiveData<LiveData<Int>>()
    val drunkenDrivingCounter: LiveData<Int>
        get() = _drunkenDrivingCounter.value!!

    private val _carelessOvertakingCounter = MutableLiveData<LiveData<Int>>()
    val carelessOvertakingCounter: LiveData<Int>
        get() = _carelessOvertakingCounter.value!!

    private val _unRoadWorthyVehicleCounter = MutableLiveData<LiveData<Int>>()
    val unRoadWorthyVehicleCounter: LiveData<Int>
        get() = _unRoadWorthyVehicleCounter.value!!

    init {
        initValueToZero()
        fetchReportedCases()
    }

    private fun initValueToZero() {
//        _overSpeedingCounter.value. = 0
//        _overLoadingCounter.value = 0
//        _drunkenDrivingCounter.value = 0
//        _carelessOvertakingCounter.value = 0
//        _unRoadWorthyVehicleCounter.value = 0
    }

    private fun fetchReportedCases() {
        val query = mDatabase.collection("reportedIncidents")
        val reportedCases = query.addSnapshotListener { querySnapshot, exception ->
            var counterOverSpeeding = 0

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
                        "Over Loading" -> overLoadingCounter.value!!.plus(1)
                        "Drunken Driving" -> drunkenDrivingCounter.value!!.plus(1)
                        "Careless Overtaking" -> carelessOvertakingCounter.value!!.plus(1)
                        "Un-Roadworthy Vehicle" -> unRoadWorthyVehicleCounter.value!!.plus(1)
                    }

                }
            }
        }

    }
}